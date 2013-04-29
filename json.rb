require 'json'
require 'pp'
require 'net/http'
require 'open-uri'

url = 'http://reddit.com/r/pics.json'   #url

buffer = open(url).read

result = JSON.parse(buffer)

pp result                               #display

