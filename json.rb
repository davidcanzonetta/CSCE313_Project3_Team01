require 'json'
require 'pp'
require 'net/http'
require 'open-uri'

url = 'http://reddit.com/.json'   #url

buffer = open(url).read

result = JSON.parse(buffer)

#result1 = result['data']['children'][0]['data']['thumbnail']

result['data']['children'].each {
  |x|

  puts x['data']['title']
  puts x['data']['url']
  puts '   '
}
                              #display






