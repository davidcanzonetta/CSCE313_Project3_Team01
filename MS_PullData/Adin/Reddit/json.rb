require 'json'
require 'pp'
require 'net/http'
require 'open-uri'
require 'snoo'

class Application

  @@title = Array.new
  @@link = Array.new

  def initialize(url)

    @url = url

    buffer = open(@url).read

    result = JSON.parse(buffer)

    result['data']['children'].each {
        |x|

      @@title.push(x['data']['title'])
      @@link.push(x['data']['url'])
    }
  end

  def page_json(num)

    puts @@title[num]
    puts @@link[num]

  end

end
#url = 'http://reddit.com/.json'   #url

##Yeahyeah = Application.new('http://reddit.com/.json')
#Yeahyeah.frontpage_json(2)
                             #display
@whatuup = Array.new
@reddit = Snoo::Client.new
if (@reddit.log_in 'sirpistolero','47zvzmev').code == 200

  whatup = @reddit.get_reddits()

  whatup['data']['children'].each{ |x|

   @whatuup.push(x['data']['url'])

  }

  pp @whatuup
end




