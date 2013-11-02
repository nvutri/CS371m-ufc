echo $1 $2
curl -X POST \
  -H "X-Parse-Application-Id: AJ0JAEbsMNs3pRi9poiROGLxopvwD9Y44aXs8rkz" \
  -H "X-Parse-REST-API-Key: ASvFj4V0kHOndsSBM2iao1oxKzqCxtaLw2w2plid" \
  -H "Content-Type: application/json" \
  -d '{
        "name": "basicInfo",
        "location": {
          "name": "'$1'",
          "__type": "File"
        }
      }' \
  https://api.parse.com/1/classes/$2
