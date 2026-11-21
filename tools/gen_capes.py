#!/usr/bin/env python3
"""Generate the cape textures shipped in assets/night/textures/capes.

Minecraft samples a cape from the 10x16 pixel area at the top-left of the
texture, so every design here is drawn on that 10x16 grid and upscaled 32x into
a 2048x1024 sheet -- the same layout as the original textures/cape.png. All
designs are original pixel art produced by this script (no gradients or shapes
are copied from another client), and the output is deterministic, so the
textures can be rebuilt byte for byte at any time.

Usage:
    python3 tools/gen_capes.py
"""
import colorsys
import math
import os

from PIL import Image

W, H, SCALE = 10, 16, 32
CANVAS = (W * SCALE * 2, H * SCALE * 2)  # 640x1024 -> padded to 2048x1024 below
OUT_DIR = os.path.join("src", "main", "resources", "assets", "night", "textures", "capes")


def clamp(value):
    return max(0, min(255, int(round(value))))


def mix(a, b, t):
    return tuple(clamp(a[i] + (b[i] - a[i]) * t) for i in range(3))


def ramp(stops, t):
    if t <= stops[0][0]:
        return stops[0][1]
    for (p0, c0), (p1, c1) in zip(stops, stops[1:]):
        if t <= p1:
            return mix(c0, c1, (t - p0) / (p1 - p0))
    return stops[-1][1]


def scale_rgb(c, factor):
    return tuple(clamp(float(v) * factor) for v in c)


def noise(x, y, seed):
    """Deterministic 0..1 hash noise, stable across runs and platforms."""
    n = (x * 374761393 + y * 668265263 + seed * 2147483647) & 0xFFFFFFFF
    n = (n ^ (n >> 13)) * 1274126177 & 0xFFFFFFFF
    return ((n ^ (n >> 16)) & 0xFFFF) / 65535.0


def aurora(x, y):
    t = y / (H - 1)
    base = ramp([(0.0, (16, 24, 52)), (0.45, (24, 92, 122)),
                 (0.78, (52, 176, 172)), (1.0, (28, 38, 88))], t)
    band = math.sin(x * 0.95 + y * 0.55 - 1.2) * 0.5 + 0.5
    return scale_rgb(base, 0.82 + 0.45 * band * (1.0 - 0.6 * t))


def circuit(x, y):
    base = (13, 17, 30)
    trace = (96, 214, 255)
    if x in (1, 8) and y % 4 != 3:
        return scale_rgb(trace, 0.55)
    if y in (3, 11) and 1 <= x <= 8:
        return scale_rgb(trace, 0.42)
    if (x, y) in ((2, 6), (5, 6), (7, 13), (3, 13)):
        return trace
    return scale_rgb(base, 0.9 + 0.2 * noise(x, y, 11))


def ember(x, y):
    t = y / (H - 1)
    base = ramp([(0.0, (12, 9, 10)), (0.55, (72, 22, 18)),
                 (0.86, (188, 74, 26)), (1.0, (247, 168, 62))], t)
    if noise(x, y, 3) > 0.9:
        return (255, 214, 132)
    return scale_rgb(base, 0.9 + 0.2 * noise(x, y, 7))


def sakura(x, y):
    t = y / (H - 1)
    base = ramp([(0.0, (42, 26, 34)), (0.4, (188, 122, 132)),
                 (0.8, (240, 190, 194)), (1.0, (252, 228, 226))], t)
    if noise(x, y, 21) > 0.88:
        return (255, 246, 245)
    if 8 <= x + y <= 11 and y > 3:
        return scale_rgb(base, 0.55)
    return base


def prism(x, y):
    hue = ((x * 0.055) + (y * 0.035)) % 1.0
    r, g, b = colorsys.hsv_to_rgb(hue, 0.62, 0.92)
    rgb = (r * 255.0, g * 255.0, b * 255.0)
    return scale_rgb(rgb, 1.0 - 0.22 * (y / (H - 1)))


def static(x, y):
    value = noise(x, y, 31)
    shade = 60 + 150 * value
    if y % 3 == 0:
        shade *= 0.6
    grey = clamp(shade)
    return (grey, grey, clamp(shade + 6))


def void(x, y):
    t = y / (H - 1)
    base = ramp([(0.0, (8, 6, 20)), (0.5, (34, 18, 64)),
                 (1.0, (12, 10, 26))], t)
    if noise(x, y, 41) > 0.93:
        return (206, 214, 255)
    return scale_rgb(base, 0.85 + 0.4 * noise(x, y, 43))


DESIGNS = {
    "aurora": aurora,
    "circuit": circuit,
    "ember": ember,
    "sakura": sakura,
    "prism": prism,
    "static": static,
    "void": void,
}


def render(fn):
    art = Image.new("RGBA", (W, H))
    for y in range(H):
        for x in range(W):
            r, g, b = fn(x, y)
            art.putpixel((x, y), (r, g, b, 255))
    big = art.resize((W * SCALE, H * SCALE), Image.NEAREST)
    sheet = Image.new("RGBA", (W * SCALE * 2, H * SCALE * 2), (0, 0, 0, 0))
    sheet.paste(big, (0, 0))
    size = (2048, 1024)
    canvas = Image.new("RGBA", size, (0, 0, 0, 0))
    canvas.paste(sheet, (0, 0))
    return canvas


def main():
    os.makedirs(OUT_DIR, exist_ok=True)
    for name, fn in DESIGNS.items():
        path = os.path.join(OUT_DIR, name + ".png")
        render(fn).save(path, "PNG", optimize=True)
        print("wrote", path, os.path.getsize(path), "bytes")


if __name__ == "__main__":
    main()
