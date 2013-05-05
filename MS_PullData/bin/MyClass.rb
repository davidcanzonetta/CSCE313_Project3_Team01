require 'json'
require 'open-uri'
require 'uri'
require 'launchy'

class Reddit #(change name)

	include GladeGUI

	@@page = 0

	@@title = Array.new
  @@link = Array.new
	
	@@saved = Hash.new
	
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

	def show()
		load_glade(__FILE__)  #loads file, glade/MyClass.glade into @builder
		
		@linkbutton1 = @@title[0 + @@page]
		@linkbutton2 = @@title[1 + @@page]
		@linkbutton3 = @@title[2 + @@page]
		@linkbutton4 = @@title[3 + @@page]
		@linkbutton5 = @@title[4 + @@page]
		@linkbutton6 = @@title[5 + @@page]
		@linkbutton7 = @@title[6 + @@page]
		@linkbutton8 = @@title[7 + @@page]

		set_glade_variables(self) # fills label with message
		@builder["window1"].title = "Reddit App" 
		
		@builder["checkbutton1"].label = "Save Post"
		@builder["checkbutton2"].label = "Save Post"
	  @builder["checkbutton3"].label = "Save Post"
		@builder["checkbutton4"].label = "Save Post"
		@builder["checkbutton5"].label = "Save Post"
		@builder["checkbutton6"].label = "Save Post"
	  @builder["checkbutton7"].label = "Save Post"

		show_window()
	end	

	def linkbutton1__clicked(*argv)
		Launchy.open(@@link[0])
	end
	def linkbutton2__clicked(*argv)
		Launchy.open(@@link[1])
	end
	def linkbutton3__clicked(*argv)
		Launchy.open(@@link[2])
	end
	def linkbutton4__clicked(*argv)
		Launchy.open(@@link[3])
	end
	def linkbutton5__clicked(*argv)
		Launchy.open(@@link[4])
	end
	def linkbutton6__clicked(*argv)
		Launchy.open(@@link[5])
	end
	def linkbutton7__clicked(*argv)
		Launchy.open(@@link[6])
	end
	def linkbutton8__clicked(*argv)
		Launchy.open(@@link[7])
	end

	def button2__clicked(*argv)				#Next button, when pressed, it will display the next page in the reddit page
	
	if @@page == 0
	
	else

	@@page = @@page - 8
	self.destroy_window	
	self.show
	end 

	end

	def button3__clicked(*argv)				#Next button, when pressed, it will display the next page in the reddit page
	
	@@page = @@page + 8
	self.destroy_window	
	self.show

	end

	def checkbutton1__toggled(*argv)			#checkbuttons: when checked, the data will be saved in a hash
		@@saved[@@title[0]] = @@link[0]				
	end	
	def checkbutton2__toggled(*argv)
		@@saved[@@title[1]] = @@link[1]				
		
	end
	def checkbutton3__toggled(*argv)
		@@saved[@@title[2]] = @@link[2]				
		
	end
	def checkbutton4__toggled(*argv)
		@@saved[@@title[3]] = @@link[3]				
		
	end
	def checkbutton5__toggled(*argv)
		@@saved[@@title[4]] = @@link[4]				
		
	end
	def checkbutton6__toggled(*argv)
		@@saved[@@title[5]] = @@link[5]				
		
	end
	def checkbutton7__toggled(*argv)		
		@@saved[@@title[6]] = @@link[6]				
	end

	def button1__clicked(*argv) #Save button, when pressed, it will save anything that it's in @@saved hash into a json file
	
		myStr = @@saved.to_json

		aFile = File.new("myString.json", "w")
		aFile.write(myStr)
		aFile.close

	end

end
