require 'snoo'
require 'twitter_oauth'
require 'launchy'
require 'json'
require 'open-uri'
require 'uri'

class RedditWindowGUI < RedditWindow #(change name)
	include GladeGUI
	
	@@page = 0							#page will be used to browse along different pages, since title and link array contain more than
													#7 posts from the homepage (title might have 27 entries)
	@@check_variable = 0 #dummy variable	

	# if load_favs = 0, newsfeed links are shown, if = 1, favs are shown
	@@load_favs = 0 

	@@title = Array.new
  @@link = Array.new
	@@num_votes = Array.new
	@@down_votes = Array.new
	@@up_votes = Array.new
	@@thread_ids = Array.new
	@@current_selection = Array.new(1)

	#Parse Favorites Json File
	json_buffer = File.read("favorites.json")
	@@topObject = JSON.parse(json_buffer)
	@@json_titles = @@topObject["titles"]
	@@json_urls = @@topObject["urls"]

	def initialize(r)
		@reddit = r
		@client = TwitterOAuth::Client.new(
	    :consumer_key => 'RMGCd8r8G5q2KuOfKB8Ycg',
	    :consumer_secret => 'OZVcqyXdPf5cYOHivZmMxcAPHE6oaaZJR0OmYmVECI'
		)	
		@reddit.get_listing().fetch('data',{}).fetch('children',{}).each do |child|
			@@title.push(child['data']['title'])
      @@link.push(child['data']['url'])
			@@num_votes.push(child['data']['score'])
			@@down_votes.push(child['data']['downs'])
			@@up_votes.push(child['data']['ups'])
			@@thread_ids.push(child['data']['id'])
		end
		@@current_selection[0] = @@thread_ids[0]
	end

	def get_postings(subred)
		@@title.clear
		@@link.clear
		@@num_votes.clear
		@@down_votes.clear
		@@up_votes.clear
		@@thread_ids.clear
		@reddit.get_listing(:subreddit => subred).fetch('data',{}).fetch('children',{}).each do |child|
			@@title.push(child['data']['title'])
      @@link.push(child['data']['url'])
			@@num_votes.push(child['data']['score'])
			@@down_votes.push(child['data']['downs'])
			@@up_votes.push(child['data']['ups'])
			@@thread_ids.push(child['data']['id'])
		end
		@@current_selection[0] = @@thread_ids[0]
	end

	def show()
		load_glade(__FILE__)
		@linkbutton1 = @@title[0 + @@page]		#at the beginning, we are using title[0+0], if we press next, page becomes 8 and this becomes title[8]
		@linkbutton2 = @@title[1 + @@page]
		@linkbutton3 = @@title[2 + @@page]
		@linkbutton4 = @@title[3 + @@page]
		@linkbutton5 = @@title[4 + @@page]
		@linkbutton6 = @@title[5 + @@page]
		@linkbutton7 = @@title[6 + @@page]
		@linkbutton8 = @@title[7 + @@page]
		set_glade_variables(self) # fills label with message
		@builder["window1"].title = "Reddit App"
		show_window()
	end

	def show_favs()
		@linkbutton1 = @@json_titles[0 + @@page]		#at the beginning, we are using title[0+0], if we press next, page becomes 8 and this becomes title[8]
		@linkbutton2 = @@json_titles[1 + @@page]
		@linkbutton3 = @@json_titles[2 + @@page]
		@linkbutton4 = @@json_titles[3 + @@page]
		@linkbutton5 = @@json_titles[4 + @@page]
		@linkbutton6 = @@json_titles[5 + @@page]
		@linkbutton7 = @@json_titles[6 + @@page]
		@linkbutton8 = @@json_titles[7 + @@page]
		@builder["loadfavs"].label = "Load Reddit Feed"
		@builder["savebutton1"].label = "Remove From Favorites"
		@builder["savebutton2"].label = "Remove From Favorites"
		@builder["savebutton3"].label = "Remove From Favorites"
		@builder["savebutton4"].label = "Remove From Favorites"
		@builder["savebutton5"].label = "Remove From Favorites"
		@builder["savebutton6"].label = "Remove From Favorites"
		@builder["savebutton7"].label = "Remove From Favorites"
		@builder["savebutton8"].label = "Remove From Favorites"
		set_glade_variables(self) # fills label with message
	end

	#decide if to save or delete when save/delete button is pressed
	def save_or_delete(num)
		if @@load_favs == 0
			save_arrays(num)	
		else
			delete_arrays(num) 
		end 
	end
	
	#save arrays to json file
	def save_arrays(num)
		#ensure title and url have not already been written
		if @@json_titles.size == 0
			@@json_titles.push @@title[num]
		else
			@@json_titles.each do |x|
				if x == @@title[num]
						@@check_variable = 1
				end
			end
			if @@check_variable != 1
				@@json_titles.push @@title[num]
			end
			@@check_variable = 0
		end
		if @@json_urls.size == 0
			@@json_urls.push @@link[num]
		else
			@@json_urls.each do |x|
				if x == @@link[num]
					@@check_variable = 1
				end	
			end
			if @@check_variable != 1
				@@json_urls.push @@link[num]
			end
			@@check_variable = 0
		end
		
		#write to json file
		File.open("favorites.json", "w") do |f|
			f.write(@@topObject.to_json)
		end
		#f.close
	end

	#delete elements from json arrays
	def delete_arrays(num)
		#delete selection from array
		@@json_titles.delete_at num
		@@json_urls.delete_at num
		
		#write to json file
		File.open("favorites.json", "w") do |f|
			f.write(@@topObject.to_json)
		end
		#f.close
	end

	def linkbutton1__clicked(*argv)
		if @@link[0+@@page] == nil				#if there is no link in the array do nothing when the button is pressed
		else															#else show the link
			Launchy.open(@@link[0+@@page])
			@builder['entry1'].text = @@num_votes[0+@@page].to_s
			@builder['upvotes_label'].text = @@up_votes[0+@@page].to_s
			@builder['downvotes_label'].text = @@down_votes[0+@@page].to_s
			@@current_selection[0] = @@thread_ids[0+@@page]
		end
	end

	def linkbutton2__clicked(*argv)
		if @@link[1+@@page] == nil
		else	
			Launchy.open(@@link[1+@@page])
			@builder['entry1'].text = @@num_votes[1+@@page].to_s
			@builder['upvotes_label'].text = @@up_votes[1+@@page].to_s
			@builder['downvotes_label'].text = @@down_votes[1+@@page].to_s
			@@current_selection[0] = @@thread_ids[1+@@page]
		end
	end

	def linkbutton3__clicked(*argv)
		if @@link[2+@@page] == nil
		else	
			Launchy.open(@@link[2+@@page])
			@builder['entry1'].text = @@num_votes[2+@@page].to_s
			@builder['upvotes_label'].text = @@up_votes[2+@@page].to_s
			@builder['downvotes_label'].text = @@down_votes[2+@@page].to_s
			@@current_selection[0] = @@thread_ids[2+@@page]
		end
	end

	def linkbutton4__clicked(*argv)
		if @@link[3+@@page] == nil
		else	
			Launchy.open(@@link[3+@@page])
			@builder['entry1'].text = @@num_votes[3+@@page].to_s
			@builder['upvotes_label'].text = @@up_votes[3+@@page].to_s
			@builder['downvotes_label'].text = @@down_votes[3+@@page].to_s
			@@current_selection[0] = @@thread_ids[3+@@page]
		end
	end

	def linkbutton5__clicked(*argv)
		if @@link[4+@@page] == nil
		else	
			Launchy.open(@@link[4+@@page])
			@builder['entry1'].text = @@num_votes[4+@@page].to_s
			@builder['upvotes_label'].text = @@up_votes[4+@@page].to_s
			@builder['downvotes_label'].text = @@down_votes[4+@@page].to_s
			@@current_selection[0] = @@thread_ids[4+@@page]
		end
	end

	def linkbutton6__clicked(*argv)
		if @@link[5+@@page] == nil
		else	
			Launchy.open(@@link[5+@@page])
			@builder['entry1'].text = @@num_votes[5+@@page].to_s
			@builder['upvotes_label'].text = @@up_votes[5+@@page].to_s
			@builder['downvotes_label'].text = @@down_votes[5+@@page].to_s
			@@current_selection[0] = @@thread_ids[5+@@page]
		end
	end

	def linkbutton7__clicked(*argv)
		if @@link[6+@@page] == nil
		else	
			Launchy.open(@@link[6+@@page])
			@builder['entry1'].text = @@num_votes[6+@@page].to_s
			@builder['upvotes_label'].text = @@up_votes[6+@@page].to_s
			@builder['downvotes_label'].text = @@down_votes[6+@@page].to_s
			@@current_selection[0] = @@thread_ids[6+@@page]
		end
	end

	def linkbutton8__clicked(*argv)
		if @@link[7+@@page] == nil
		else	
			Launchy.open(@@link[7+@@page])
			@builder['entry1'].text = @@num_votes[7+@@page].to_s
			@builder['upvotes_label'].text = @@up_votes[7+@@page].to_s
			@builder['downvotes_label'].text = @@down_votes[7+@@page].to_s
			@@current_selection[0] = @@thread_ids[7+@@page]
		end
	end

	def refresh_links() 
		@linkbutton1 = @@title[0 + @@page]		#at the beginning, we are using title[0+0], if we press next, page becomes 8 and this becomes title[8]
		@linkbutton2 = @@title[1 + @@page]
		@linkbutton3 = @@title[2 + @@page]
		@linkbutton4 = @@title[3 + @@page]
		@linkbutton5 = @@title[4 + @@page]
		@linkbutton6 = @@title[5 + @@page]
		@linkbutton7 = @@title[6 + @@page]
		@linkbutton8 = @@title[7 + @@page]
		set_glade_variables(self)
	end

	#Previous button, when pressed, it will display the previous page in the reddit page
	def previous__clicked(*argv)				
		if @@page == 0 
		else
			@@page = @@page - 8
			refresh_links()
			if @@load_favs != 0	
				show_favs()
			end
		end 
	end

	#Next button, when pressed, it will display the next page in the reddit page
	def next__clicked(*argv)				
		if (@@page == @@json_titles.length && @@load_favs == 1) # if nothing on next or previous page, do nothing
		else		
			@@page = @@page + 8
			refresh_links()
			if @@load_favs != 0	
				show_favs()
			end
		end
	end

	#When save button pressed, that title and link are saved
	def savebutton1__clicked(*argv)
		save_or_delete(0+@@page)		
	end
	
	def savebutton2__clicked(*argv)	
		save_or_delete(1 + @@page)			
	end

	def savebutton3__clicked(*argv)
		save_or_delete(2 + @@page)				
	end

	def savebutton4__clicked(*argv)
		save_or_delete(3 + @@page)				
	end

	def savebutton5__clicked(*argv)	
		save_or_delete(4 + @@page)			
	end

	def savebutton6__clicked(*argv)
		save_or_delete(5 + @@page)			
	end

	def savebutton7__clicked(*argv)		
		save_or_delete(6 + @@page)			
	end

	def savebutton8__clicked(*argv)		
		save_or_delete(7 + @@page)			
	end

	def loadfavs__clicked(*argv)		
		@@page = 0
		if @@load_favs == 0
			@@load_favs = 1
			show_favs()
		else
			@@load_favs = 0
			refresh_links()
		end		
	end

		def twitterbutton1__clicked(*argv)
		if @@logged_in == true
			@@twitter_client.update("Check this out! " + @@link[0+@@page])
		end
	end
	
	def twitterbutton2__clicked(*argv)
		if @@logged_in == true
			@@twitter_client.update("Whoa! " + @@link[1+@@page])
		end
	end
	
	def twitterbutton3__clicked(*argv)
		if @@logged_in == true
			@@twitter_client.update("Look what I found. " + @@link[2+@@page])
		end
	end

	def twitterbutton4__clicked(*argv)
		if @@logged_in == true
			@@twitter_client.update("Got this from the best app ever. " + @@link[3+@@page])
		end
	end

	def twitterbutton5__clicked(*argv)
		if @@logged_in == true
			@@twitter_client.update("Mind. Blown. " + @@link[4+@@page])
		end
	end

	def twitterbutton6__clicked(*argv)
		if @@logged_in == true
			@@twitter_client.update("#hashtag " + @@link[5+@@page])
		end
	end

	def twitterbutton7__clicked(*argv)
		if @@logged_in == true
			@@twitter_client.update("Almost as cool as Drake. #awesome " + @@link[6+@@page])
		end
	end

	def twitterbutton8__clicked(*argv)
		if @@logged_in == true
			@@twitter_client.update("#brilliant " + @@link[7+@@page])
		end
	end

	def button1__clicked(*argv) 
		@twitterWin = TwitterLoginWindow.new(self) #self = parent
		@twitterWin.show(self)
	end
	
	def from_child(client)
		client.update("Tweeting from ruby")
	end

	def on_twitterlogin_activate
		# log in to twitter
		@twitterWin = TwitterLoginWindow.new(self) #self = parent
		@twitterWin.show(self)
	end
	
	def from_child(client)
		#client.update("I'm kind of a big deal")
		@@logged_in = true
		@builder["twitterbutton1"].sensitive = true
		@builder["twitterbutton2"].sensitive = true
		@builder["twitterbutton3"].sensitive = true
		@builder["twitterbutton4"].sensitive = true
		@builder["twitterbutton5"].sensitive = true
		@builder["twitterbutton6"].sensitive = true
		@builder["twitterbutton7"].sensitive = true
		@builder["twitterbutton8"].sensitive = true
		@@twitter_client = client
	end

	def list_subreddits
		@subred = SubReddit.new(self, @reddit)
		@subred.show()
	end

	def save_url(suburl)
		@subred_url = suburl
		@subred_url = @subred_url.split("/").last
		get_postings(@subred_url)
		refresh_links()
	end

	def commentbutton__clicked(*argv)
		@comment = @builder["commentbox"].text
		@id = "t3_" + @@current_selection[0]
		
		@reddit.comment @comment, @id
		@commentbox = ""
		set_glade_variables(self)
	end
	
	def button1__clicked(*argv)
		@id = "t3_" + @@current_selection[0]
		@reddit.upvote @id
	end

	def button2__clicked(*argv)
		@id = "t3_" + @@current_selection[0]
		@reddit.downvote @id
	end

end
