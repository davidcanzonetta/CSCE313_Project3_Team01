require 'json'
require 'open-uri'
require 'uri'
require 'launchy'

class Reddit #(change name)

	include GladeGUI

	@@page = 0							#page will be used to browse along different pages, since title and link array contain more than
													#7 posts from the homepage (title might have 27 entries)
	@@check_variable = 0 #dummy variable	

	# if load_favs = 0, newsfeed links are shown, if = 1, favs are shown
	@@load_favs = 0 

	@@title = Array.new
  @@link = Array.new

	#Parse Favorites Json File
	json_buffer = File.read("favorites.json")
	@@topObject = JSON.parse(json_buffer)
	@@json_titles = @@topObject["titles"]
	@@json_urls = @@topObject["urls"]

	def initialize()
		
		#Parse Reddit Json
    @url = 'http://reddit.com/.json'

    buffer = open(@url).read

    result = JSON.parse(buffer)

    result['data']['children'].each {
        |x|

      @@title.push(x['data']['title'])
      @@link.push(x['data']['url'])
    }	
  end

	def show()
		load_glade(__FILE__)  #loads file, glade/MyClass.glade into @builder

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
		load_glade(__FILE__)
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
		@builder["window1"].title = "Reddit App"
		show_window()
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
		end
	end

	def linkbutton2__clicked(*argv)
		if @@link[1+@@page] == nil
		else	
			Launchy.open(@@link[1+@@page])
		end
	end

	def linkbutton3__clicked(*argv)
		if @@link[2+@@page] == nil
		else	
			Launchy.open(@@link[2+@@page])
		end
	end

	def linkbutton4__clicked(*argv)
		if @@link[3+@@page] == nil
		else	
			Launchy.open(@@link[3+@@page])
		end
	end

	def linkbutton5__clicked(*argv)
		if @@link[4+@@page] == nil
		else	
			Launchy.open(@@link[4+@@page])
		end
	end

	def linkbutton6__clicked(*argv)
		if @@link[5+@@page] == nil
		else	
			Launchy.open(@@link[5+@@page])
		end
	end

	def linkbutton7__clicked(*argv)
		if @@link[6+@@page] == nil
		else	
			Launchy.open(@@link[6+@@page])
		end
	end

	def linkbutton8__clicked(*argv)
		if @@link[7+@@page] == nil
		else	
			Launchy.open(@@link[7+@@page])
		end
	end

	#Previous button, when pressed, it will display the previous page in the reddit page
	def previous__clicked(*argv)				
		if @@page == 0 
		else
			@@page = @@page - 8
			destroy_window()
			if @@load_favs == 0	
				show()
			else
				show_favs()
			end
		end 
	end

	#Next button, when pressed, it will display the next page in the reddit page
	def next__clicked(*argv)				
		if (@@page == 24 && @@load_favs == 0) || (@@page == @@json_titles.length && @@load_favs == 1) # if nothing on next or previous page, do nothing
		else		
			@@page = @@page + 8
			destroy_window()
			if @@load_favs == 0	
				show()
			else
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
		destroy_window()
		@@page = 0
		if @@load_favs == 0
			@@load_favs = 1
			show_favs()
		else
			@@load_favs = 0
			show()
		end		
	end

end
