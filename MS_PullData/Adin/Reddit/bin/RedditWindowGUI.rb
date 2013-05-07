class RedditWindowGUI #< RedditWindow #(change name)

	include GladeGUI

	def show(parent)
		load_glade(__FILE__)
		set_glade_variables(self)
		show_window()
	end

	def button1__clicked(*argv) 
		@twitterWin = TwitterLoginWindow.new(self) #self = parent
		@twitterWin.show(self)
	end
	
	def from_child(client)
		client.update("Tweeting from ruby")
	end

end
