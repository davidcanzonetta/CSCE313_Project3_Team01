class MyClass #(change name)
	include GladeGUI

	def show()
		load_glade(__FILE__)  #loads file, glade/MyClass.glade into @builder
		@builder["window1"].title = "MyClass" # appears in window title bar
		show_window()  #calls Gtk.main
	end	

	def loginButton__clicked(*argv)
		reddit_win = Reddit.new() #self = parent
		reddit_win.show(self)
	end

	# This method is unnecessary, see MyModalClass.
	def cancelButton__clicked(*argv)
		destroy_window()
	end

end


