
class URL_Input  #(change name)

	include GladeGUI
	def initialize(parent)

	@parent = parent
	end 
	def show()
		load_glade(__FILE__)
		#load_glade(__FILE__,parent)
		#set_glade_variables(self) #populates glade controls with insance variables 		
		show_window()
	end	

  def button1__clicked(*argv)
	@parent.set_subreddit('http://reddit.com/r/' + @builder["entry1"].text + '.json')
	self.destroy_window()
	end

	def button2__clicked(*argv)
	self.destroy_window()
	end
end
