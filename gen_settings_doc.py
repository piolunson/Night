import re,glob,os,json
from collections import OrderedDict

def split_top(s):
    out=[];depth=0;cur='';q=None;i=0
    while i<len(s):
        c=s[i]
        if q:
            cur+=c
            if c=='\\': cur+=s[i+1]; i+=1
            elif c==q: q=None
        elif c in '"\'':
            q=c;cur+=c
        elif c in '([{':
            depth+=1;cur+=c
        elif c in ')]}':
            depth-=1;cur+=c
        elif c==',' and depth==0:
            out.append(cur.strip());cur=''
        else: cur+=c
        i+=1
    if cur.strip(): out.append(cur.strip())
    return out

def unq(s):
    s=s.strip()
    if len(s)>=2 and s[0]=='"' and s[-1]=='"':
        return s[1:-1].replace('\\"','"').replace('\\\\','\\')
    return s

mod_re=re.compile(r'@RegisterModule\(\s*name\s*=\s*"([^"]*)"\s*,\s*description\s*=\s*"([^"]*)"\s*,\s*category\s*=\s*Module\.Category\.(\w+)',re.S)
field_re=re.compile(r'(?:(?:public|private|protected|static|final)\s+)*(\w+Setting\w*)\s+(\w+)\s*=\s*new\s+(\w+)\(',re.S)

modules=[]
for path in sorted(glob.glob('src/main/java/night/modules/impl/**/*.java',recursive=True)):
    src=open(path,encoding='utf-8').read()
    m=mod_re.search(src)
    if not m: continue
    name,desc,cat=m.group(1),m.group(2),m.group(3)
    settings=[]
    for fm in field_re.finditer(src):
        typ,field,ctor=fm.group(1),fm.group(2),fm.group(3)
        if 'Setting' not in typ: continue
        # find matching close paren of ctor
        i=fm.end(); depth=1; q=None
        while i<len(src) and depth>0:
            c=src[i]
            if q:
                if c=='\\': i+=1
                elif c==q: q=None
            elif c in '"\'': q=c
            elif c=='(': depth+=1
            elif c==')': depth-=1
            i+=1
        args=split_top(src[fm.end():i-1])
        if len(args)<2: continue
        # name / optional tag / description
        has_tag=False
        if len(args)>=3 and args[0].startswith('"') and args[1].startswith('"') and args[2].startswith('"'):
            a1=unq(args[1]); a2=unq(args[2])
            has_tag = len(a1)<=24 and (a2.endswith('.') or len(a2)>len(a1)+15)
        if has_tag:
            nm=unq(args[0]); tag=unq(args[1]); ds=unq(args[2]); rest=args[3:]
        else:
            nm=unq(args[0]); tag=None; ds=unq(args[1]); rest=args[2:]
        vis=None
        while rest and ('Visibility(' in rest[0] or rest[0].startswith('new ModeSetting.Visibility') or rest[0].startswith('new CategorySetting.Visibility')):
            if vis is None:
                vm=re.search(r'Visibility\(\s*(?:this\.)?(\w+)\s*,\s*([^)]*)\)',rest[0])
                if vm:
                    opts=', '.join(unq(x) for x in split_top(vm.group(2)))
                    vis='%s = %s' % (vm.group(1),opts)
            rest=rest[1:]
        default=''; extra=''
        if typ=='BooleanSetting' or (rest and rest[0] in ('true','false')):
            default=rest[0] if rest else ''
        elif typ=='NumberSetting':
            nums=[r for r in rest if re.fullmatch(r'-?\d+(\.\d+)?[fFdD]?',r)]
            if nums:
                default=nums[0]
                if len(nums)>=3: extra='%s – %s' % (nums[1],nums[2])
                elif len(nums)==2: extra='min %s' % nums[1]
        elif typ=='ModeSetting':
            if rest:
                default=unq(rest[0]) if rest[0].startswith('"') else rest[0]
                mm=re.search(r'\{(.*)\}',','.join(rest[1:]),re.S)
                if mm: extra=', '.join(unq(x) for x in split_top(mm.group(1)))
        elif typ=='StringSetting':
            default=unq(rest[0]) if rest else ''
        elif typ=='ColorSetting':
            default=rest[0] if rest else ''
            if len(rest)>1: extra=', '.join(rest[1:])
        elif typ in ('BindSetting','ImageSetting','PositionSetting','CategorySetting'):
            default='' if typ!='CategorySetting' else ''
            if rest: extra=', '.join(rest[:2])
        else:
            default=rest[0] if rest else ''
            if len(rest)>1: extra=', '.join(rest[1:3])
        default=default.replace('Color.','').replace('new Color','Color')
        settings.append(dict(field=field,type=typ,name=nm,tag=tag,desc=ds,default=default,extra=extra,vis=vis))
    modules.append(dict(name=name,desc=desc,cat=cat,settings=settings,file=path))

order=['COMBAT','MOVEMENT','PLAYER','VISUALS','RENDER','WORLD','MISC','CLIENT','CORE','EXPLOIT','HUD']
cats=OrderedDict()
for m in modules: cats.setdefault(m['cat'],[]).append(m)
for c in cats: cats[c].sort(key=lambda x:x['name'])

total=sum(len(m['settings']) for m in modules)
out=[]
out.append('# Night 26.2 — kompletny opis ustawień\n')
out.append('Dokument wygenerowany automatycznie z kodu źródłowego (`src/main/java/night/modules`). Zawiera **%d modułów** i **%d ustawień**.\n' % (len(modules),total))
out.append('## Jak czytać tabele\n')
out.append('- **Ustawienie** — nazwa widoczna w GUI i używana w komendach (`<module> <setting> <value>`).\n- **Typ** — klasa ustawienia: `Boolean` (przełącznik), `Number` (suwak/liczba), `Mode` (lista wyboru), `String` (tekst), `Color` (kolor), `Bind` (klawisz), `Whitelist` (lista bloków/itemów), `Image`, `Position`, `Category` (nagłówek grupy).\n- **Domyślnie** — wartość startowa.\n- **Zakres / opcje** — dopuszczalny zakres liczbowy lub lista dostępnych wartości.\n- **Grupa** — ustawienie widoczne tylko gdy wskazane ustawienie przyjmuje podane wartości.\n- **Tag** — krótka etykieta wyświetlana w GUI obok nazwy.\n')
for c,ms in cats.items():
    out.append('\n## Kategoria: %s (%d modułów)\n' % (c,len(ms)))
    for m in ms:
        out.append('\n### %s\n' % m['name'])
        out.append('%s\n\n' % m['desc'])
        out.append('Plik: `%s`\n\n' % m['file'].replace('src/main/java/',''))
        if not m['settings']:
            out.append('_Moduł nie posiada własnych ustawień._\n')
            continue
        out.append('| Ustawienie | Typ | Domyślnie | Zakres / opcje | Grupa | Tag | Opis |\n')
        out.append('|---|---|---|---|---|---|---|\n')
        for s in m['settings']:
            desc=s['desc'].replace('|','\\|')
            out.append('| `%s` | %s | %s | %s | %s | %s | %s |\n' % (
                s['name'], s['type'].replace('Setting',''), s['default'].replace('|','\\|'),
                s['extra'].replace('|','\\|'), (s['vis'] or '').replace('|','\\|'),
                s['tag'] or '', desc))
out.append('\n---\n\nLiczba modułów: **%d**, liczba ustawień: **%d**.\n' % (len(modules),total))
os.makedirs('docs',exist_ok=True)
open('docs/SETTINGS.md','w',encoding='utf-8').write(''.join(out))
json.dump(modules,open('settings_index.json','w',encoding='utf-8'),ensure_ascii=False,indent=1)
print('modules',len(modules),'settings',total,'bytes',os.path.getsize('docs/SETTINGS.md'))
