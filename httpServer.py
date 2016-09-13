import http.server
import socketserver
import os

os.chdir("./img/")

PORT = 8080

Handler = http.server.SimpleHTTPRequestHandler

httpd = socketserver.TCPServer(("", PORT), Handler)

print("serving at port", PORT)
httpd.serve_forever()
