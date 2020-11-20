# Smart Calendar

## Table of Contents
1. [Overview](#Overview)
2. [Product Spec](#Product-Spec)
3. [Wireframes](#Wireframes)

## Overview
### Description
Smart Calendar is an AI-powered calendar app that allows the user to simplify usage by simply taking a picture or a screenshot of a poster or any text to automatically input events, meetings, tasks, etc. into their calendar, providing the user with a new sense of convenience.

### App Evaluation
* **Category**: Productivity, Lifestyle, Calendar
* **Mobile**: This app would be primarily developed for Android but would sync with the google calendar. Thus changes to the google calendar regardless of the device would be reflected in the app. Changes can be made within the app as well.
* **Story**: The app functions normally as a calendar app like Google Calendar or Apple Calendar, but the catch is that it has AI integrated features which allows the user to simplify usage by simply taking a picture or a screenshot of a poster or any text to automatically input events, meetings, tasks, etc. into their calendar, and provide the user with a new sense of convenience.
* **Market**: All age groups and demographics could use this app, there is no niche segmented target market that. People who actively use Calendars whether Google Calendar or Outlook would be our early adopters.
* **Habit**: This app could be used multiple times a day to schedule and organize events around peoples lives. As many times as they need to add, delete, or edit calendar events they will use it. Especially when they are away from a laptop/desktop and only have their phone on for convenience.
* **Scope**: First we have our calendar have syncing capabilites with Google calendar. Also Users can create, update, edit or delete calendar events and it will be on sync in the app and on google calendar. Calendar event details include date, location, reminder, and description. Users will also be use their camera to take pictures that and auto-fill out calendar event details. Later we might allow more details for events, linking accounts, offline storage and inviting other users to events.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can login
* Push notification to remind user of events/tasks
* User can see a daily and monthly calendar view 
* User can manually input, modify, and delete items on their calendar
* User can use their camera to take a photo of texts to create items on thier calendar 

**Optional Nice-to-have Stories**

* User can color code items on their calendar
* User can set the priority of items on thier calendar
* User can invite others to events or meetings
* User can link multiple accounts
* User can switch accounts
* User can access their calendar offline

### 2. Screen Archetypes

* Login Screen
   * User can login
* Stream Screen (Monthly)
   * User can view the calendar for the month
* Stream Screen (Daily)
   * User can view the agenda of a day
* Detail Screen
   * User can view the details of an item on the calendar or delete it
* Creation Screen
   * User can create or modify an item on the calendar
   
### 3. Navigation

**Flow Navigation** (Screen to Screen)

* Login Screen
   => Calendar
* Stream Screen (Monthly)
   => Stream Screen (Daily) / Detail Screen / Creation Screen
* Stream Screen (Daily)
   => Stream Screen (Monthly) / Detail Screen / Creation Screen
* Detail Screen
   => Stream Screen (Monthly) / Stream Screen (Daily) / Creation Screen
* Creation Screen 
   => Stream Screen (Monthly) / Stream Screen (Daily) / Detail Screen 

## Wireframes
<img src="https://github.com/YilikaLoufoua/SmartCalendar/blob/main/Hand-Sketched%20Wireframes.jpg" width=1000>

### [BONUS] Digital Wireframes & Mockups
<img src="https://github.com/YilikaLoufoua/SmartCalendar/blob/main/Digital%20Wireframes.png" width=1000>

### [BONUS] Interactive Prototype
<img src="https://github.com/YilikaLoufoua/SmartCalendar/blob/main/Interactive%20Prototype.gif" width=350>

## Smart Calendar - Scheme Design
<ol>
<li> Login Activity</li>
<li> Calendar</li>
<li> Day View</li>
<li> ActivityDetails</li>
<li> ActivityEdit</li>
</ol>

<ol>
<li> Login Activity</li>
     <ul><li>Image</li>
     <li>Login Authorization</li>
     <li>Login Authentication</li>
       <ol><li>Signed out</li></ol></ul>

<li> Calendar</li>
     <ul><li>Year</li>
     <li>Month</li>
     <li>MonthView</li>
       <ol><li>Days of Week</li>
       <li>Day Focus</li></ol>
     <li>DayView</li>
     <li>recyclerView</li>
       <ol><li>Events</li>
         <ol><li>Title</li>
         <ol><li>Time</li></ol></ul>

<li> Day View</li>
     <ul><li>MenuBackArrow</li>
       <ol><li>Month</li>
       <li>Date</li>
       <li>Day</li></ol>
     <li>recyclerView</li>
       <ol><li>Post</li>
         <ol><li>Title</li></ol></ol></ul>

<li> ActivityDetails</li>
     <ul><li>MenuBackArrow</li>
     <li>Edit/PencilIcon</li>
     <li>Delete/TrashIcon</li>
     <li>Title</li>
     <li>dateTime</li>
       <ol><li>Month</li>
       <li>Date</li>
       <li>Day</li></ol>
     <li>Location</li>
       <ol><li>Pin</li>
       <li>Location</li>
       <li>Address</li></ol>
     <li>Notification</li>
       <ol><li>Reminder/BellIcon</li>
       <li>ReminderOccurence</li></ol>
     <li>Decription</li>
       <ol><li>Notes/MenuIcon</li>
       <li>Notes</li></ol></ul>

<li> ActivityEdit</li>
     <ul><li>Close/XIcon</li>
     <li>Done/CheckIcon</li>
     <li>Divider</li>
     <li>clockIcon</li>
     <li>addStartDate</li>
     <li>addStartTime</li>
     <li>addEndDate</li>
     <li>addEndTime</li>
     <li>Divider</li>
     <li>addLocation</li>
     <li>Divider</li>
     <li>addNotification</li>
     <li>Divider</li>
     <li>addDescription</li></ul>
</ol>

## Data Model 

<table>
	<tr><td> </td> <td>Property</td><td>Type</td><td>Description</td></tr>
<tr><td>Login Activity</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>objectId</td> <td>File</td> <td>pointer to calendar image</td> </tr>
	<tr><td> </td> <td>Login Authentication</td> <td>API call</td> <td>authenticate user with Google</td> </tr>
	<tr><td> </td> <td>Login Authorization/td> <td>API call</td> <td>user authorized app for use with Google</td> </tr>
  <tr><td> </td> <td>Signed out</td> <td>File</td> <td>has the User signed out</td> </tr>
	
<tr><td>Calendar</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Year</td> <td>Date Time</td> <td>Calls Year</td></tr>
	<tr><td> </td> <td>Month</td> <td>Date Time<</td> <td>Calls Month</td></tr>
	<tr><td> </td> <td>MonthView</td> <td>Calendar Item</td> <td>displays selected month Calendar</td></tr>
	<tr><td> </td> <td>Days of Week</td> <td>String</td> <td>Days of week</td></tr>
	<tr><td> </td> <td>Focus</td> <td>Background Highlight</td> <td>Highlighted if event exists that day</td> </tr>
	<tr><td> </td> <td>DayView</td> <td>Text</td> <td>Selected Date in text / Today by default</td> </tr>
	<tr><td> </td> <td>recyclerView</td> <td>recyclerView</td> <td>Lists event for selected day in chronological order</td> </tr>
	<tr><td> </td> <td>Events</td> <td>DateTime</td> <td>date when meme was last updated</td> </tr>
  <tr><td> </td> <td>Title</td> <td>Test</td> <td>Title of Event</td> </tr>
  <tr><td> </td> <td>Time</td> <td>DateTime</td> <td>Time of Event</td> </tr>
	
<tr><td>Day View</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Menu Back Arrow</td> <td>Menu Item</td> <td>sends user back to Calendar</td> </tr>
	<tr><td> </td> <td>Month</td> <td>DateTime</td> <td>Call to Event Month</td> </tr>
	<tr><td> </td> <td>Date</td> <td>DateTime</td> <td>Call to Event Day</td> </tr>
	<tr><td> </td> <td>Day</td> <td>DateTime</td> <td>Call to Event Day</td> </tr>
  <tr><td> </td> <td>recyclerView</td> <td>recyclerView</td> <td>Lists event for selected day in chronological order</td> </tr>
  <tr><td> </td> <td>Post</td> <td>Highlight</td> <td>Shading of Event</td> </tr>
  <tr><td> </td> <td>Title</td> <td>Text</td> <td>Title of Event</td> </tr>

<tr><td>ActivityDetails</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Menu Back Arrow</td> <td>Menu Item</td> <td>sends user back to Calendar</td> </tr>
	<tr><td> </td> <td>Menu Edit</td> <td>Image</td> <td>Pencil Icon -leads to Activity Edit</td> </tr>
	<tr><td> </td> <td>Menu Delete</td> <td>Image</td> <td>Pencil Icon -leads to Activity Delete</td> </tr>
  <tr><td> </td> <td>Title</td> <td>String</td> <td>Event Title</td> </tr>
  <tr><td> </td> <td>dateTime</td> <td>Date Time</td> <td>Day, Month, Date, to - from Time</td> </tr>
  <tr><td> </td> <td>Pin Icon</td> <td>Icon</td> <td>Pin Image</td> </tr>
  <tr><td> </td> <td>Location</td> <td>String</td> <td>Event Location</td> </tr>
  <tr><td> </td> <td>Address</td> <td>String</td> <td>Event Address</td> </tr>
  <tr><td> </td> <td>Bell Icon</td> <td>Icon</td> <td>Bell Image</td> </tr>
  <tr><td> </td> <td>ReminderOccurence</td> <td>String</td> <td>Event Reminder</td> </tr>
  <tr><td> </td> <td>Menu Icon</td> <td>Icon</td> <td>Menu Image</td> </tr>
  <tr><td> </td> <td>Reminder</td> <td>String</td> <td>Notes</td> </tr>  
	
<tr><td>ActivityEdit</td> <td> </td> <td> </td> <td> </td> </tr>
	<tr><td> </td> <td>Menu Close</td> <td>Icon</td> <td>Closes Edit Activity</td> </tr>
	<tr><td> </td> <td>Title</td> <td>String</td> <td>Event Title</td> </tr>
	<tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
  <tr><td> </td> <td>Clock Icon</td> <td>Icon</td> <td>Clock Image</td> </tr>
  <tr><td> </td> <td>dateTime</td> <td>Date Time</td> <td>Day, Month, Date, from Time</td> </tr>
  <tr><td> </td> <td>dateTime</td> <td>Date Time</td> <td>Day, Month, Date, to Time</td> </tr>
  <tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
  <tr><td> </td> <td>Pin Icon</td> <td>Icon</td> <td>Pin Image</td> </tr>
  <tr><td> </td> <td>Location</td> <td>String</td> <td>Event Location</td> </tr>
  <tr><td> </td> <td>Address</td> <td>String</td> <td>Event Address</td> </tr>
  <tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
  <tr><td> </td> <td>Bell Icon</td> <td>Icon</td> <td>Bell Image</td> </tr>
  <tr><td> </td> <td>ReminderOccurence</td> <td>String</td> <td>Event Reminder</td> </tr>
  <tr><td> </td> <td>addNotification</td> <td>String</td> <td>add another Notification</td> </tr>
  <tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>
  <tr><td> </td> <td>Menu Icon</td> <td>Icon</td> <td>Menu Image</td> </tr>
  <tr><td> </td> <td>Note</td> <td>String</td> <td>Notes Text</td> </tr>
  <tr><td> </td> <td>Divider</td> <td>Line</td> <td>Line Item</td> </tr>  

</table>


<table>
	<tr><th>Network Requests</th> <td> </td> <td> </td> </tr>
	<tr><td>CRUD</td> <td> HTTP VERB </td> <td> Example </td> </tr>
	<tr><td>Create</td> <td> POST </td> <td> Creating a new event </td> </tr>
	<tr><td>Read</td> <td> GET </td> <td> Reading from Google API </td> </tr>
	<tr><td>Update</td> <td> PUT </td> <td> Changing a user’s calendar Event</td> </tr>
	<tr><td>Delete</td> <td> DELETE </td> <td> Deleting an event </td> </tr>
</table>
