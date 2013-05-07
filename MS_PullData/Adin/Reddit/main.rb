#!/usr/bin/ruby

require 'vrlib'



#make program output in real time so errors visible in VR.
STDOUT.sync = true
STDERR.sync = true

my_path = File.expand_path(File.dirname(__FILE__))
require_all Dir.glob(my_path + "/bin/**/*.rb") 

RedditLoginWindow.new.show

