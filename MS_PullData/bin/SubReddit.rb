require 'snoo'
require 'json'

class SubReddit #(change name)
	
	include GladeGUI

	@@page = 0
	@@holder = Array.new
	@@subreddits = Array.new
	
   	def initialize(parent,reddit_username)
				@parent = parent
				
				@reddit = Snoo::Client.new
				if (@reddit.log_in 'sirpistolero','47zvzmev').code == 200 
  			@@holder = @reddit.get_reddits()

 			 	@@holder['data']['children'].each{ |x|

   			@@subreddits.push(x['data']['url'])
  			}			
  		end
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
				@builder["button4"].label = 'http://reddit.com' + @@subreddits[2 + @@page] 
			end
	
			if @@subreddits[4 + @@page] == nil
				@builder["button5"].label = ''
			else 
				@builder["button5"].label = 'http://reddit.com' + @@subreddits[3 + @@page] 
			end

			if @@subreddits[5 + @@page] == nil
				@builder["button6"].label = ''
			else 
				@builder["button6"].label = 'http://reddit.com' + @@subreddits[4 + @@page] 
			end

			if @@subreddits[6 + @@page] == nil
				@builder["button7"].label = ''
			else 
				@builder["button7"].label = 'http://reddit.com' + @@subreddits[5 + @@page] 
			end

			if @@subreddits[7 + @@page] == nil
				@builder["button8"].label = ''
			else 
				@builder["button8"].label = 'http://reddit.com' + @@subreddits[6 + @@page] 
			end				
			show_window()
  	end	
  
  	def button1__clicked(*argv)
			self.destroy_window()
			@parent.save_url(@builder["button1"].label)  		
  	end
  	def button2__clicked(*argv)
			self.destroy_window()
			@parent.save_url(@builder["button2"].label)
  	end
  	def button3__clicked(*argv)
			self.destroy_window()
			@parent.save_url(@builder["button3"].label)  		
  	end
  	def button4__clicked(*argv)
			 		self.destroy_window()
			@parent.save_url(@builder["button4"].label)			
  	end
  	def button5__clicked(*argv)
			self.destroy_window()
			@parent.save_url(@builder["button5"].label)  		
  	end
  	def button6__clicked(*argv)
			self.destroy_window()
			@parent.save_url(@builder["button6"].label)
  	end
  	def button7__clicked(*argv)
			self.destroy_window()			
			@parent.save_url(@builder["button7"].label)			
  	end
  	def button8__clicked(*argv)
			self.destroy_window()
			@parent.save_url(@builder["button8"].label)			
  	end
  	
		def button9__clicked(*argv)
  		if @@page != 0 
				@@page = @@page - 8 
				self.destroy_window
				self.show
				end
			end
  	def button10__clicked(*argv)
			if @@page <= @@subreddits.length
				@@page = @@page + 8
  			self.destroy_window
				self.show
			
  		end
		end
end
