package com.revature.scheduler.services;

import com.revature.scheduler.daos.EventDAO;
import com.revature.scheduler.daos.LocationDAO;
import com.revature.scheduler.daos.UserDAO;
import com.revature.scheduler.dtos.EventDTO;
import com.revature.scheduler.models.Event;
import com.revature.scheduler.models.Location;
import com.revature.scheduler.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {

    private final UserDAO userDAO;
    private final EventDAO eventDAO;

    private final LocationDAO locationDAO;

    @Autowired
    public EventService(UserDAO userDAO, EventDAO eventDAO, LocationDAO locationDAO) {
        this.userDAO = userDAO;
        this.eventDAO = eventDAO;
        this.locationDAO = locationDAO;
    }

    public Event getEventById(int eventId) {
        return eventDAO.findById(eventId).get();
    }

    public Event createEvent(int userId, EventDTO eventDTO){
        Event e= new Event();
        e.setName(eventDTO.getName());
        e.setDate(eventDTO.getDate());
        e.setStartTime(eventDTO.getStartTime());
        e.setEndTime(eventDTO.getEndTime());
        e.setType(eventDTO.getType());

        User u = userDAO.findById(userId).get();
        e.setAuthor(u);

        Location l = eventDTO.getLocation();
        locationDAO.save(l);
        e.setLocation(l);

        return eventDAO.save(e);
    }

    public Event updateEventById(int eventId, Event event) {
        Event e = eventDAO.getReferenceById(eventId);

        if(event.getName() != null){
            e.setName(event.getName());
        }
        if(event.getDate() != null){
            e.setDate(event.getDate());
        }
        if(event.getStartTime()!=null){
            e.setStartTime(event.getStartTime());
        }
        if(event.getEndTime()!=null){
            e.setEndTime(event.getEndTime());
        }
        if(event.getType()!=null){
            e.setType(event.getType());
        }
        if(event.getLocation() !=null){
            Location l = event.getLocation();
            Location oldL = e.getLocation();
            locationDAO.save(l);
            e.setLocation(l);
            locationDAO.delete(oldL);
        }

        return eventDAO.save(e);
    }
}