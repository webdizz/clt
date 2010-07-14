# Generated by Buildr 1.4.0, change to your liking
# Version number for this release
VERSION_NUMBER = "0.0.1-SNAPSHOT"
# Group identifier for your projects
GROUP = "name.webdizz.liner"
COPYRIGHT = ""

# Specify Maven 2.0 remote repositories here, like this:
repositories.remote << "http://localhost:8081/nexus/content/groups/public/"

GWT_GROUP = 'com.google.gwt'
GWT_VERSION = '2.0.3'
GWT = [
	"#{GWT_GROUP}:gwt-dev:jar:#{GWT_VERSION}",
	"#{GWT_GROUP}:gwt-servlet:jar:#{GWT_VERSION}",
	"#{GWT_GROUP}:gwt-user:jar:#{GWT_VERSION}"
]

desc "The Clt project"
define "liner" do

  project.version = VERSION_NUMBER
  project.group = GROUP
  manifest["Implementation-Vendor"] = COPYRIGHT

  define "clt-crx" do
  end

  define "gwt-chrome-bridge" do
	compile.with GWT
	test.with 'org.mockito:mockito-all:jar:1.8.3', 'junit:junit:jar:4.8.1'
	package :jar
  end

  define "gwt-chrome-bridge-test" do
  end

end
