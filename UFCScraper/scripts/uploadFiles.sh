echo $1
curl -X POST \
  -H "X-Parse-Application-Id: AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz" \
  -H "X-Parse-REST-API-Key: ASvFj4V0kHOndsSBM2iao1oxKzqCxtaLw2w2plid" \
  -H "Content-Type: text/plain" \
  --data-binary "@$1" \
  https://api.parse.com/1/files/$1
