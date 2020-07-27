# coding=utf-8
import time
import hashlib
import urllib2
import os

timestamp = str(int(time.time()))
appkey = '你的appkey'
secret = '你的secret'

message = '+'.join([appkey, secret, timestamp])

m = hashlib.md5()
m.update(message)
signature = m.hexdigest()

http_url = 'https://open.mobvoi.com/api/tts/v1?appkey={appkey}&signature={signature}&timestamp={timestamp}&text={text}&speaker={speaker}'

http_url = http_url.format(appkey=appkey,signature=signature,timestamp=timestamp,text="欢迎使用出门问问开放平台",speaker="tina");

print(http_url)

try:
    # buld http request
    req = urllib2.Request(http_url)
    # header
    req.add_header('User-Agent', 'Mozilla/5.0')
    # post data to server
    resp = urllib2.urlopen(req, timeout=5)
    # get response
    data = resp.read()

    with open(os.path.join(os.path.dirname(os.path.abspath("__file__")), "demo.mp3"), "wb") as f:
        f.write(data)

except Exception, e:
    print 'http error'