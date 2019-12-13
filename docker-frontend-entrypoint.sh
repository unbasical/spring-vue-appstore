#!/bin/bash
sed -i 's@<BACKEND_URL>@'"$BACKEND_URL"'@' /usr/share/nginx/html/index.html
exec "$@"