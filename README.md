# simple-REST
an example of REST using spring boot consists of GET, C, PUT, DELETE requests to API.

GET
curl -X GET \
  http://localhost:8080/user/
  
POST
curl -X POST \
  http://localhost:8080/user/ \
  -H 'content-type: application/json' \
  -d '{
	"name":"novandi",
	"age":25,
	"salary":"123456789"
}'
