require 'snoo'

class RedditLoginWindow #(change name)
	@reddit
	include GladeGUI
	def initialize()
		@reddit = Snoo::Client.new
	end

	def show()
		load_glade(__FILE__)  #loads file, glade/MyClass.glade into @builder
		#set_glade_all() #populates glade controls with insance variables (i.e. Myclass.label1) 
		show_window() 
	end	

	def button1__clicked(*argv) 
		@username = @builder["entry1"].text
		@password = @builder["entry2"].text
		if @username.size > 0 && @password.size > 0
			if (@reddit.log_in @username, @password).code == 200
				@redditWin = RedditWindowGUI.new() 
							
				@redditWin.show
				self.destroy_window()							
			end
		else
				@builder["label3"].text = "Please enter a valid username and/or password"
		end

	end

end

