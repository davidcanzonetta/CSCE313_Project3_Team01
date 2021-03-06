require 'twitter_oauth'
require 'launchy'

class TwitterLoginWindow #(change name)

	include GladeGUI

	def initialize(parent) 
		@parent = parent
		@client = TwitterOAuth::Client.new(
	    :consumer_key => 'RMGCd8r8G5q2KuOfKB8Ycg',
	    :consumer_secret => 'OZVcqyXdPf5cYOHivZmMxcAPHE6oaaZJR0OmYmVECI'
		)
	end

	def show(parent)		
		load_glade(__FILE__, parent)  # now child will close with parent
		#set_glade_all() #populates glade controls with insance variables (i.e. Myclass.label1) 	
		show_window() 
	end	

	def twitterlogin__clicked(*argv)
		if(@builder["entry1"].text.size == 0)
			@request = @client.request_token
			@url = @request.authorize_url
			@parsed_url = URI.parse( @url )
			Launchy.open( @parsed_url )
		end

		@builder["entry1"].sensitive = true

		if(@builder["entry1"].text.size > 0)
			@pin = @builder["entry1"].text.to_i
			@account = @client.authorize(
			  @request.token,
			  @request.secret,
			  :oauth_verifier => @pin
			)
			if @client.authorized?
				@parent.from_child(@client)
			end
			destroy_window()
		end

	end
end
