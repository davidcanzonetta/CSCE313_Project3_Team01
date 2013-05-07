require 'snoo'
require 'json'

class SubReddit #(change name)

@reddit = Snoo::Client.new
if (@reddit.log_in 'sirpistolero','47zvzmev').code == 200
				
	include GladeGUI
	def initialize()
	end
	def show()
		load_glade(__FILE__)  #loads file, glade/MyClass.glade into @builder
		show_window()
	end	

	def button1__clicked(*argv)
	destroy_window()
	end
	def button2__clicked(*argv)

	end
	def button3__clicked(*argv)

	end
	def button4__clicked(*argv)

	end
	def button5__clicked(*argv)

	end
	def button6__clicked(*argv)

	end
	def button7__clicked(*argv)

	end
	def button8__clicked(*argv)

	end
	def button9__clicked(*argv)

	end
	def button10__clicked(*argv)

	end
end
end
