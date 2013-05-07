class RedditWindowGUI #< RedditWindow #(change name)

	include GladeGUI

	def show()
		load_glade(__FILE__)
		show_window()
	end

	def button1__clicked(*argv) 
		@twitterWin = TwitterLoginWindow.new() #self = parent
		@twitterWin.show(self)
	end

end
