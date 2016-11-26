########### Python 2.7 #############
import httplib, urllib, base64

headers = {
    # Request headers
    'Content-Type': 'application/json',
    'Ocp-Apim-Subscription-Key': '688b20aa2b044f60895922eca734a7fd',
}

params = urllib.urlencode({
    # Request parameters
    'visualFeatures': 'Categories',
    'details': '{string}',
    'language': 'en',
})

try:
    conn = httplib.HTTPSConnection('api.projectoxford.ai')
    conn.request("POST", "/vision/v1.0/analyze?%s" % params, "{body}", headers)
    response = conn.getresponse()
    data = response.read()
    print(data)
    conn.close()
except Exception as e:
    print("[Errno {0}] {1}".format(e.errno, e.strerror))

####################################

########### Python 3.2 #############
import http.client, urllib.request, urllib.parse, urllib.error, base64

headers = {
    # Request headers
    'Content-Type': 'application/json',
    'Ocp-Apim-Subscription-Key': '{subscription key}',
}

params = urllib.parse.urlencode({
    # Request parameters
    'visualFeatures': 'Categories',
    'details': '{string}',
    'language': 'en',
})

try:
    conn = http.client.HTTPSConnection('api.projectoxford.ai')
    conn.request("POST", "/vision/v1.0/analyze?%s" % params, "{body}", headers)
    response = conn.getresponse()
    data = response.read()
    print(data)
    conn.close()
except Exception as e:
    print("[Errno {0}] {1}".format(e.errno, e.strerror))

####################################