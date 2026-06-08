mkdir /tmp/Firewall.iconset

for size in 16 32 128 256 512; do
  inkscape ../icon.svg --export-type=png --export-width=$size --export-filename=/tmp/Firewall.iconset/icon_${size}x${size}.png
  inkscape ../icon.svg --export-type=png --export-width=$((2*size)) --export-filename=/tmp/Firewall.iconset/icon_${size}x${size}@2x.png
done

iconutil -c icns /tmp/Firewall.iconset
mv /tmp/Firewall.icns ../