# Smart Calendar

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Smart Calendar is an AI-enpowered calendar app that allows the user to simplify usage by simply taking a picture or a screenshot of a poster or any text to automatically input events, meetings, tasks, etc. into their calendar, providing the user with a new sense of convenience.

### App Evaluation
- **Category:**
- **Mobile:**
- **Story:**
- **Market:**
- **Habit:**
- **Scope:**

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can login
* Push notification to remind user of events/reminders/tasks
* User can see a basic daily, weekly, and monthly calendar view 
* User can manually input, modify, and delete items on their calendar
* User can use their camera to take a photo of texts to create items on thier calendar 

**Optional Nice-to-have Stories**

* User can color code items on their calendar
* User can set the priority of items on thier calendar
* User can invite others to events or meetings
* User can view their calendar offline
* User can link multiple accounts

### 2. Screen Archetypes

* Login Screen
   * User can login
* Calendar
   * User can view the calendar for the month
   * User can create an item on the calendar
* Stream Screen
   * User can view the agenda of each day
   * User can create an item on the calendar
* Detail Screen
   * User can view the details of an item on the calendar
* Creation Screen
   * User can create, modify, or delete an item on the calendar
   
### 3. Navigation

**Flow Navigation** (Screen to Screen)

* Login Screen
   => Calendar
* Calendar
   => Stream Screen / Creation Screen
* Stream Screen
   => Calendar / Detail Screen / Creation Screen
* Detail Screen
   => Stream Screen / Creation Screen
* Creation Screen 
   => Stream Screen / Detail Screen 

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups

### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
[Add table of models]
### Networking
- [Add list of network requests by screen ]
- [Create basic snippets for each Parse network request]
- [OPTIONAL: List endpoints if using existing API such as Yelp]
