require 'snoo'

class RedditLoginWindow #(change name)

	include GladeGUI

	def initialize
		@reddit = Snoo::Client.new
	end

	def show()
		load_glade(__FILE__)  #loads file, glade/MyClass.glade into @builder
		set_glade_all() #populates glade controls with insance variables (i.e. Myclass.label)
		@builder["window1"].title = "Reddit Login"
		show_window() 
	end	
	
	def button1__clicked(*argv) 
		@username = @builder["entry1"].text
		@password = @builder["entry2"].text

		begin
		  @reddit.log_in @username, @password
			@redditWin = RedditWindowGUI.new(@reddit)
			self.destroy_window()
			@redditWin.show()
		rescue 
		  VR::Dialog.message_box("You have entered an incorrect username and/or password. Please try again", title="Login Error")
		end
	end

end

