# Smart Calendar

## Table of Contents
1. [Overview](#Overview)
2. [Product Spec](#Product-Spec)
3. [Wireframes](#Wireframes)
3. [Schema](#Schema)

## Overview
### Description
Smart Calendar is an AI-powered calendar app that allows the user to simplify usage by simply taking a picture or a screenshot of a poster or any text to automatically input events, meetings, tasks, etc. into their calendar, providing the user with a new sense of convenience.

### Demo

<img src='https://github.com/SmartCalendar/SmartCalendar/blob/main/demo.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />


### App Evaluation
* **Category**: Productivity, Lifestyle, Calendar
* **Mobile**: This app would be primarily developed for Android but would sync with the google calendar. Thus changes to the google calendar regardless of the device would be reflected in the app. Changes can be made within the app as well.
* **Story**: The app functions normally as a calendar app like Google Calendar or Apple Calendar, but the catch is that it has AI integrated features which allows the user to simplify usage by simply taking a picture or a screenshot of a poster or any text to automatically input events, meetings, tasks, etc. into their calendar, and provide the user with a new sense of convenience.
* **Market**: All age groups and demographics could use this app, there is no niche segmented target market that. People who actively use Calendars whether Google Calendar or Outlook would be our early adopters.
* **Habit**: This app could be used multiple times a day to schedule and organize events around peoples lives. As many times as they need to add, delete, or edit calendar events they will use it. Especially when they are away from a laptop/desktop and only have their phone on for convenience.
* **Scope**: First we have our calendar have syncing capabilites with Google calendar. Also Users can create, update, edit or delete calendar events and it will be on sync in the app and on google calendar. Calendar event details include date, location, reminder, and description. Users will also be use their camera to take pictures that and auto-fill out calendar event details. Later we might allow more details for events, linking accounts, offline storage and inviting other users to events.

## Product Spec

### 1. User Stories (Required and Optional)

** Wire Framing **
- [x] Wire Framing

** Data Modeling**
- [x] Data Model / Wireframe - completed via Imgur

**Required Must-have Stories**

* [x] User can login
* [x] Push notification to remind user of events/tasks
* [x] User can see a daily calendar view
* [x] User can see a monthly calendar view 
* [x] User can manually input, modify, and delete events on their calendar
* [x] User can use their camera to take a photo of texts to create events on thier calendar 

**Optional Nice-to-have Stories**

* [ ] User can color code events on their calendar
* [ ] User can set the priority of events on thier calendar
* [ ] User can invite others to events or meetings
* [ ] User can link multiple accounts
* [ ] User can switch accounts
* [ ] User can access their calendar offline

### 2. Screen Archetypes

* [x] Login Screen
   * [X] User can login
* [x] Stream Screen (Monthly)
   * [x] User can view the calendar for the month
* [x] Stream Screen (Daily)
   * [x] User can view the agenda of a day
* [x] Detail Screen
   * [x] User can view the details of an event on the calendar or delete it
* [x] Creation Screen
   * [x] User can create or modify an event on the calendar
   
### 3. Navigation

**Flow Navigation** (Screen to Screen)

* [x] Login Screen
   => Calendar
* [x] Stream Screen (Monthly)
   => Stream Screen (Daily) / Detail Screen / Creation Screen
* [x] Stream Screen (Daily)
   => Stream Screen (Monthly) / Detail Screen / Creation Screen
* [x] Detail Screen
   => Stream Screen (Monthly) / Stream Screen (Daily) / Creation Screen
* [x] Creation Screen 
   => Stream Screen (Monthly) / Stream Screen (Daily) / Detail Screen 

## Wireframes
<img src="https://github.com/YilikaLoufoua/SmartCalendar/blob/main/Hand-Sketched%20Wireframes.jpg" width=1000>

### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/YilikaLoufoua/SmartCalendar/blob/main/Digital%20Wireframes.png" width=1000>

### [BONUS] Interactive Prototype
<img src="https://github.com/YilikaLoufoua/SmartCalendar/blob/main/Interactive%20Prototype.gif" width=350>

### Floating Action Menu
<img src="https://github.com/SmartCalendar/SmartCalendar/blob/main/floatingActionMenu.gif" width=350>

### Logging in with Federated Login - Google API
<img src="https://github.com/SmartCalendar/SmartCalendar/blob/main/loginAPI.gif" width=350>

### Edit Activity, Date and Time Widgets, and Notification Dialog Fragment
<img src="https://github.com/SmartCalendar/SmartCalendar/blob/main/editbtn_notificationmenu.gif" width=350>

### High Priority Notifications with reminders before event time done with AlarmManager & BroadCast Receiver
<img src= "https://github.com/SmartCalendar/SmartCalendar/blob/main/notification_popup2.gif" width=350>

### Dynamic Monthly View generation algorithm+view
<img src = "https://github.com/SmartCalendar/SmartCalendar/blob/main/monthview_btns_test2.gif" width=350>

## Schema
<ol>
<li> Login </li>
<li> Monthly View </li>
<li> Daily View </li>
<li> Detail </li>
<li> Edit </li>
<li> Notification </li>
<li> Parse </li>
<li> Camera </li>
<li> Events </li>
</ol>

<ol>
<li> Login </li>
     <ul><li>Image </li>
     <li>Login User Name </li>
     <li>Login Password </li></ul>
	
<li> Monthly View </li>
     <ul><li>Year</li>
     <li>Month </li>
     <li>Directional Arrows </li>
     <li>Week Days </li>
     <li>Dates </li>
     <li>Events </li></ul>

<li> Daily View </li>
     <ul><li>Year</li>
     <li>Month</li>
     <li>Recycler View</li>
       <ol><li>Date</li>
       <li>Day</li>
       <ol><li>Event</li>
       <li>Time</li></ol></ol></ul>

<li> Details</li>
     <ul><li>Close X </li>
     <li>Edit / Pencil Icon</li>
     <li>Delete / Trash Icon</li>
     <li>Title</li>
     <li>Date Time</li>
       <ol><li>Month</li>
       <li>Date</li>
       <li>Day</li></ol>
     <li>Location</li>
       <ol><li>Pin</li>
       <li>Location</li>
       <li>Address</li></ol>
     <li>Notification</li>
       <ol><li>Reminder / Bell Icon</li>
       <li>Reminder Occurence</li></ol>
     <li>Decription</li>
       <ol><li>Notes / MenuIcon</li>
       <li>Notes</li></ol></ul>

<li> Edit </li>
     <ul><li>Close (X) Icon</li>
     <li>Done (Check) Icon</li>
     <li>Divider</li>
     <li>Clock Icon</li>
     <li>Add Start Date</li>
     <li>Add Start Time</li>
     <li>Add End Date</li>
     <li>Add End Time</li>
     <li>Divider</li>
     <li>Add Location</li>
     <li>Divider</li>
     <li>Add Notification</li>
     <li>Divider</li>
     <li>Add Description</li></ul>

<li> Notifications</li>
	<ul><li>Extracted Text</li></ul>

<li> Camera</li>
	<ul><li>Image</li>
	<li>Extracted Text</li></ol>

## Data Model 

<table>
	<tr><td> </td> <td>Property</td><td>Type</td><td>Description</td></tr>
<tr><td>Login Activity</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>objectId</td> <td>File</td> <td>pointer to calendar image</td> </tr>
	<tr><td> </td> <td>Login User Name</td> <td>API call</td> <td>authenticate user with Parse Server</td> </tr>
	<tr><td> </td> <td>Login Password</td> <td>API call</td> <td>user authorized for use with Parse Server</td> </tr>

<tr><td>Monthly View</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Year</td> <td>Date Time</td> <td>Call to Month</td> </tr>
	<tr><td> </td> <td>Month</td> <td>DateTime</td> <td>Call to Event Month</td> </tr>
	<tr><td> </td> <td>Directional Arrows</td> <td>Text</td> <td>Sends user to Previous or next Month</td> </tr>
	<tr><td> </td> <td>Week Days</td> <td>Text</td> <td>Sunday through Saturday</td> </tr>
	<tr><td> </td> <td>Date</td> <td>DateTime</td> <td>Call to Event Day</td> </tr>
	<tr><td> </td> <td>Dates</td> <td>Text</td> <td>Call to days in month</td> </tr>
  	<tr><td> </td> <td>Events</td> <td>Highlight</td> <td>Shading of Event</td> </tr>

<tr><td>Day View</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Year</td> <td>Date Time</td> <td>Call to Month</td> </tr>
	<tr><td> </td> <td>Month</td> <td>DateTime</td> <td>Call to Event Month</td> </tr>
	<tr><td> </td> <td>recyclerView</td> <td>recyclerView</td> <td>Lists event for selected day in chronological order</td> </tr>
	<tr><td> </td> <td>Date</td> <td>DateTime</td> <td>Call to Event Day</td> </tr>
	<tr><td> </td> <td>Day</td> <td>DateTime</td> <td>Call to Event Day</td> </tr>
  	<tr><td> </td> <td>Post</td> <td>Highlight</td> <td>Shading of Event</td> </tr>
  	<tr><td> </td> <td>Title</td> <td>Text</td> <td>Title of Event</td> </tr>
	<tr><td> </td> <td>Time</td> <td>Text</td> <td>Time of Event</td> </tr>

<tr><td>Details</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Close X</td> <td>Text</td> <td>sends user back to Calendar</td> </tr>
	<tr><td> </td> <td>Menu Edit</td> <td>Image</td> <td>Pencil Icon -leads to Activity Edit</td> </tr>
	<tr><td> </td> <td>Menu Delete</td> <td>Image</td> <td>Trash Icon -leads to Activity Delete</td> </tr>
  	<tr><td> </td> <td>Title</td> <td>String</td> <td>Event Title</td> </tr>
  	<tr><td> </td> <td>Date Time</td> <td>Date Time</td> <td>Day, Month, Date, to - from Time</td> </tr>
  	<tr><td> </td> <td>Pin Icon</td> <td>Icon</td> <td>Pin Image</td> </tr>
  	<tr><td> </td> <td>Location</td> <td>String</td> <td>Event Location</td> </tr>
  	<tr><td> </td> <td>Address</td> <td>String</td> <td>Event Address</td> </tr>
  	<tr><td> </td> <td>Bell Icon</td> <td>Icon</td> <td>Bell Image</td> </tr>
  	<tr><td> </td> <td>Reminder Occurence</td> <td>String</td> <td>Event Reminder</td> </tr>
  	<tr><td> </td> <td>Menu Icon</td> <td>Icon</td> <td>Menu Image</td> </tr>
  	<tr><td> </td> <td>Reminder</td> <td>String</td> <td>Notes</td> </tr>  
	
<tr><td>Edit</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Close</td> <td>Icon</td> <td>Closes</td> </tr>
	<tr><td> </td> <td>Edit</td> <td>Icon</td> <td>Complete</td> </tr>
	<tr><td> </td> <td>Title</td> <td>String</td> <td>Event Title</td> </tr>
	<tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
  	<tr><td> </td> <td>Clock Icon</td> <td>Icon</td> <td>Clock Image</td> </tr>
  	<tr><td> </td> <td>Start Time</td> <td>Date Time</td> <td>Day, Month, Date, from Time</td> </tr>
  	<tr><td> </td> <td>End Time</td> <td>Date Time</td> <td>Day, Month, Date, to Time</td> </tr>
  	<tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
  	<tr><td> </td> <td>Pin Icon</td> <td>Icon</td> <td>Pin Image</td> </tr>
  	<tr><td> </td> <td>Location</td> <td>String</td> <td>Event Location</td> </tr>
  	<tr><td> </td> <td>Address</td> <td>String</td> <td>Event Address</td> </tr>
  	<tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
  	<tr><td> </td> <td>Bell Icon</td> <td>Icon</td> <td>Bell Image</td> </tr>
  	<tr><td> </td> <td>Reminder Occurence</td> <td>String</td> <td>Event Reminder</td> </tr>
  	<tr><td> </td> <td>Add Notification</td> <td>String</td> <td>Add another Notification</td> </tr>
  	<tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
  	<tr><td> </td> <td>Menu Icon</td> <td>Icon</td> <td>Menu Image</td> </tr>
  	<tr><td> </td> <td>Note</td> <td>String</td> <td>Notes Text</td> </tr>
  	<tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
	<tr><td> </td> <td>Description</td> <td>Text</td> <td>Detail Text</td> </tr> 
  
<tr><td>ImageActivity</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Image</td> <td>Media Object</td> <td>Image taken by User</td> </tr>
	<tr><td> </td> <td>Extracted Test</td> <td>String</td> <td>Text extracted from image</td> </tr>
	
<tr><td>Event</td> <td> </td> <td> </td> <td> </td> </tr>
  	<tr><td> </td> <td>title</td> <td>String</td> <td>Event title</td> </tr>
  	<tr><td> </td> <td>date</td> <td>Date</td> <td>Starting date/time of the event</td> </tr>
  	<tr><td> </td> <td>end_date</td> <td>Date</td> <td>Ending date/time of the event</td> </tr>
  	<tr><td> </td> <td>location</td> <td>String</td> <td>Event location</td> </tr>
  	<tr><td> </td> <td>notification_minutes_before</td> <td>int</td> <td>Number of minutes before the event</td> </tr>
  	<tr><td> </td> <td>description</td> <td>String</td> <td>Description of the event</td> </tr>
  	<tr><td> </td> <td>objectId</td> <td>String</td> <td>Unique ID of the event object</td> </tr>
  	<tr><td> </td> <td>user</td> <td>Pointer to User</td> <td>Unique ID of the user</td> </tr>
</table>

<table>
	<tr><th>Network Requests</th> <td> </td> <td> </td> </tr>
	<tr><td>CRUD</td> <td> HTTP VERB </td> <td> Example </td> </tr>
	<tr><td>Create</td> <td> POST </td> <td> Creating a new event </td> </tr>
	<tr><td>Read</td> <td> GET </td> <td> Reading from Google API </td> </tr>
	<tr><td>Update</td> <td> PUT </td> <td> Changing a user’s calendar Event</td> </tr>
	<tr><td>Delete</td> <td> DELETE </td> <td> Deleting an event </td> </tr>
</table>

## Network Requests 

<table>
<tr><th>-Login Activity</th><th> </th><th> </th></tr>
  	<tr><td> </td>
	<td>(Create/POST)</td>
	<td>Image</td></tr>
	<tr><td> </td>
        <td>(Read/GET)</td>
	<td>Parse API</td></tr>
	<tr><td> </td>	
	<td>(Read/GET)</td>
	<td>Parse User Authentication</td></tr>
	<tr><td> </td>	
	<td>(Read/GET)</td>
	<td>Parse User Authorization</td></tr>
	
  <tr><th>-Monthly View</th><th> </th><th> </th></tr>
   	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Year</td></tr>
	<tr><td> </td>
	<td>((Read/GET)</td>
	<td>Month</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Week Days</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Dates</td></tr>
	<tr><td> </td>	
	<td>(Read/GET)</td>
	<td>Event Title</td></tr>
	
  <tr><th>-Day View</th><th> </th><th> </th></tr>
  	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Year</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Month</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Date</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Day</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Event</td></tr>
	<tr><td> </td>	
	<td>(Read/GET)</td>
	<td>Event Title</td></tr>
	
  <tr><th>-Details</th><th> </th><th> </th></tr>
  	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>Event</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Title</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Date</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Location</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Reminder</td></tr>
	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Notes</td></tr>
	
  <tr><th>-Edit</th><th> </th><th> </th></tr>
  	<tr><td> </td>
	<td>(Read/GET)</td>
	<td>Event</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>Title</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>Start Date</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>Start Time</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>End Date</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>End Time</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>Location</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>Address</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>Reminder</td></tr>
	<tr><td> </td>
	<td>(Create/Post)</td>
	<td>Add Notification</td></tr>
	<tr><td> </td>
	<td>(Update/PUT)</td>
	<td>Notes</td></tr>
	<tr><td> </td>
	<td>(Create/POST)</td>
	<td>Confirm Event Changes</td></tr>
	
<tr><th>-Notifications</th><th> </th><th> </th></tr>
 	<tr><td> </td>
        <td>(Read/GET)</td>
	<td>Text</td></tr>

<tr><th>-Camera</th><th> </th><th> </th></tr>
	 <tr><td> </td>
        <td>(Create/POST)</td>
	<td>Time</td></tr>
  	<tr><td> </td>
        <td>(Create/POST)</td>
	<td>Text</td></tr>


</table>
