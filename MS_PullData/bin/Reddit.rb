require 'json'
require 'open-uri'

class Reddit #(change name)

  include GladeGUI

	@@title = Array.new
  @@link = Array.new

  def initialize()

    @url = 'http://reddit.com/.json'

    buffer = open(@url).read

    result = JSON.parse(buffer)

    result['data']['children'].each {
        |x|

      @@title.push(x['data']['title'])
      @@link.push(x['data']['url'])
    }
  end

	def show(parent)
		load_glade(__FILE__, parent)  #loads file, glade/MyClass.glade into @builder
		@linkbutton1 = @@title[0]
		@linkbutton2 = @@title[1]
		@linkbutton3 = @@title[2]
		@linkbutton4 = @@title[3]
		@linkbutton5 = @@title[4]
		@linkbutton6 = @@title[5]
		@linkbutton7 = @@title[6]
		@linkbutton8 = @@title[7]
		set_glade_variables(self) # fills label with message
		@builder["window1"].title = "Reddit App" 
		show_window()
	end	

	def linkbutton1__clicked(*argv)

	end
end