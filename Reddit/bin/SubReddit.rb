require 'snoo'
require 'json'

class SubReddit #(change name)
	
	include GladeGUI

	@@page = 0
	@@holder = Array.new
	@@subreddits = Array.new
	
   	def initialize(parent,reddit)
				@parent = parent
				
				@reddit = reddit
  			@@holder = @reddit.get_reddits()

 			 	@@holder['data']['children'].each{ |x|
   				@@subreddits.push(x['data']['url'])
  			}			
		end

  	def show()
  		load_glade(__FILE__)  #loads file, glade/MyClass.glade into @builder
			
			if @@subreddits[0 + @@page] == nil
				@builder["button1"].label = ''
			else 
				@builder["button1"].label = 'http://reddit.com' + @@subreddits[0 + @@page] 
			end
			
			if @@subreddits[1 + @@page] == nil
				@builder["button2"].label = ''
			else 
				@builder["button2"].label = 'http://reddit.com' + @@subreddits[1 + @@page] 
			end
			
			if @@subreddits[2 + @@page] == nil
				@builder["button3"].label = ''
			else 
				@builder["button3"].label = 'http://reddit.com' + @@subreddits[2 + @@page] 
			end
			
			if @@subreddits[3 + @@page] == nil
				@builder["button4"].label = ''
			else 
				@builder["button4"].label = 'http://reddit.com' + @@subreddits[3 + @@page] 
			end
	
			if @@subreddits[4 + @@page] == nil
				@builder["button5"].label = ''
			else 
				@builder["button5"].label = 'http://reddit.com' + @@subreddits[4 + @@page] 
			end

			if @@subreddits[5 + @@page] == nil
				@builder["button6"].label = ''
			else 
				@builder["button6"].label = 'http://reddit.com' + @@subreddits[5 + @@page] 
			end

			if @@subreddits[6 + @@page] == nil
				@builder["button7"].label = ''
			else 
				@builder["button7"].label = 'http://reddit.com' + @@subreddits[6 + @@page] 
			end

			if @@subreddits[7 + @@page] == nil
				@builder["button8"].label = ''
			else 
				@builder["button8"].label = 'http://reddit.com' + @@subreddits[7 + @@page] 
			end				
			show_window()
  	end	

		def refresh_links() 
			if @@subreddits[0 + @@page] == nil
				@builder["button1"].label = ''
			else 
				@builder["button1"].label = 'http://reddit.com' + @@subreddits[0 + @@page] 
			end
			
			if @@subreddits[1 + @@page] == nil
				@builder["button2"].label = ''
			else 
				@builder["button2"].label = 'http://reddit.com' + @@subreddits[1 + @@page] 
			end
			
			if @@subreddits[2 + @@page] == nil
				@builder["button3"].label = ''
			else 
				@builder["button3"].label = 'http://reddit.com' + @@subreddits[2 + @@page] 
			end
			
			if @@subreddits[3 + @@page] == nil
				@builder["button4"].label = ''
			else 
				@builder["button4"].label = 'http://reddit.com' + @@subreddits[3 + @@page] 
			end
	
			if @@subreddits[4 + @@page] == nil
				@builder["button5"].label = ''
			else 
				@builder["button5"].label = 'http://reddit.com' + @@subreddits[4 + @@page] 
			end

			if @@subreddits[5 + @@page] == nil
				@builder["button6"].label = ''
			else 
				@builder["button6"].label = 'http://reddit.com' + @@subreddits[5 + @@page] 
			end

			if @@subreddits[6 + @@page] == nil
				@builder["button7"].label = ''
			else 
				@builder["button7"].label = 'http://reddit.com' + @@subreddits[6 + @@page] 
			end

			if @@subreddits[7 + @@page] == nil
				@builder["button8"].label = ''
			else 
				@builder["button8"].label = 'http://reddit.com' + @@subreddits[7 + @@page] 
			end	
			set_glade_variables(self)
		end
  
  	def button1__clicked(*argv)
			@parent.save_url(@@subreddits[0 + @@page])
			self.destroy_window() 		
  	end
  	def button2__clicked(*argv)
			@parent.save_url(@@subreddits[1 + @@page])
			self.destroy_window()
  	end
  	def button3__clicked(*argv)
			@parent.save_url(@@subreddits[2 + @@page])
			self.destroy_window()  		
  	end
  	def button4__clicked(*argv)
			@parent.save_url(@@subreddits[3 + @@page])
			self.destroy_window()			
  	end
  	def button5__clicked(*argv)
			@parent.save_url(@@subreddits[4 + @@page])
			self.destroy_window()  		
  	end
  	def button6__clicked(*argv)
			@parent.save_url(@@subreddits[5 + @@page])
			self.destroy_window()
  	end
  	def button7__clicked(*argv)			
			@parent.save_url(@@subreddits[6 + @@page])
			self.destroy_window()			
  	end
  	def button8__clicked(*argv)
			@parent.save_url(@@subreddits[7 + @@page])
			self.destroy_window()
  	end
  	
		def button9__clicked(*argv)
  		if @@page != 0 
				@@page = @@page - 8 
				refresh_links
				end
			end
  	def button10__clicked(*argv)
			if @@page <= @@subreddits.length
				@@page = @@page + 8
  			refresh_links			
  		end
		end
end
