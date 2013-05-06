
class RedditWindow #(change name)

	include GladeGUI
	
	def show()
		load_glade(__FILE__)
		show_window()
	end

end
