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
<li>1. Login Activity</li>
<li>2. Calendar</li>
<li>3. Day View</li>
<li>4. ActivityDetails</li>
<li>5. ActivityEdit</li>
</ol>

<ol>
<li>1. Login Activity</li>
     <ul><li>=>objectID / Image</li>
     <li>=>Login Authorization</li>
     <li>=>Login Authentication</li>
     <li>=>Signed out</li>

<li>2. Calendar</li>
     <ul><li>=>objectId Year</li>
     <li>=>objectId Month</li>
     <li>=>objectId MonthView</li>
       <li>=>objectId Days of Week</li>
       <li>=>objectId Day Focus</li>
     <li>=>objectId DayView</li>
     <li>=>recyclerView</li>
       <li>=>Events</li></ul>

<li>3. Day View</li>
     <ul><li>=>objectIDd Date</li>
       <li>=>objectId Month</li>
       <li>=>objectId Date</li>
       <li>=>objectId Day</li>
     <li>=>recyclerView</li>
       <li>=>objectId Post</li>
         <li>=>objectId Title</li></ul>

<li>4. ActivityDetails</li>
     <ul><li>=>objectId MenuBackArrow</li>
     <li>=>objectId Edit/PencilIcon</li>
     <li>=>objectId Delete/TrashIcon</li>
     <li>=>objectId Title</li>
     <li>=>objectId dateTime</li>
       <li>=>objectId Month</li>
       <li>=>objectId Date</li>
       <li>=>objectId Day</li>
     <li>=>objectId Location</li>
       <li>=>objectId Pin</li>
       <li>=>objectId Location</li>
       <li>=>objectId Address</li>
     <li>=>objectId Notification</li>
       <li>=>objectId Reminder/BellIcon</li>
       <li>=>objectId ReminderOccurence</li>
     <li>=>objectId Decription</li>
       <li>=>objectId Notes/MenuIcon</li>
       <li>=>objectId Notes</li></ul>

<li>5. ActivityEdit</li>
     <ul><li>=>objectId Close/XIcon</li>
     <li>=>objectId Done/CheckIcon</li>
     <li>=>objectId Divider</li>
     <li>=>objectId clockIcon</li>
     <li>=>objectId addStartDate</li>
     <li>=>objectId addStartTime</li>
     <li>=>objectId addEndDate</li>
     <li>=>objectId addEndTime</li>
     <li>=>objectId Divider</li>
     <li>=>objectId addLocation</li>
     <li>=>objectId Divider</li>
     <li>=>objectId addNotification</li>
     <li>=>objectId Divider</li>
     <li>=>objectId addDescription</li></ul>
</ol>
