import re,subprocess,sys,os
env=dict(os.environ)
def compile_():
    r=subprocess.run(['./gradlew','compileJava','--no-daemon','--console=plain'],capture_output=True,text=True,env=env)
    open('build.log','w').write(r.stdout+r.stderr)
    return open('build.log').read()
defs={'boolean':'false','int':'0','long':'0L','double':'0.0','float':'0.0f','char':'(char)0','byte':'(byte)0','short':'(short)0','String':'null','List':None}
for it in range(12):
    log=compile_()
    errs=re.findall(r'(/\S+\.java):(\d+): error: variable (\w+) might not have been initialized',log)
    if not errs:
        print("NO_VAR_ERRORS", log.count('error:'))
        break
    done=set()
    for f,line,var in errs:
        if (f,var) in done: continue
        done.add((f,var))
        L=open(f).read().splitlines()
        # find declaration line: "Type var;" above the reported line
        idx=int(line)-1
        pat=re.compile(r'^(\s*)([\w\.\<\>\[\], \?]+?)\s+'+re.escape(var)+r'\s*;$')
        for j in range(idx,-1,-1):
            m=pat.match(L[j])
            if m:
                typ=m.group(2).strip()
                base=typ.split('<')[0]
                d=defs.get(base,'null')
                if d is None: d='null'
                L[j]='%s%s %s = %s;' % (m.group(1),typ,var,d)
                open(f,'w').write('\n'.join(L)+'\n')
                print('init',f.split('/')[-1],var,typ,'->',d)
                break
        else:
            print('nodecl',f,var)
print("END")
