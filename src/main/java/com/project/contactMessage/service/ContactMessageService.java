package com.project.contactMessage.service;

import com.project.contactMessage.dto.ContactMessageRequest;
import com.project.contactMessage.dto.ContactMessageResponse;
import com.project.contactMessage.entity.ContactMessage;
import com.project.contactMessage.mapper.ContactMessageMapper;
import com.project.contactMessage.messages.Messages;
import com.project.contactMessage.repository.ContactMessageRepository;
import com.project.exception.ConflictException;
import com.project.exception.ResourceNotFoundException;
import com.project.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor // Kotlin data class'lari arastir , bununla yazm imkani var entity data classlarimizi
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;


    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {


        ContactMessage contactMessage = contactMessageMapper.requestToContactMessage(contactMessageRequest);
        ContactMessage savedData = contactMessageRepository.save(contactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact Message Created Successfully")
                .httpStatus(HttpStatus.CREATED) // 201
                .object(contactMessageMapper.contactMessageToResponse(savedData))
                .build();


    }

    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findAll(pageable).map(contactMessageMapper::contactMessageToResponse);

    }

    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {

        // Ici on voit la meme methode qu'en haut , ce n'est pas la bonne pratique ,
        // normalement on peut utiliser une autre class comme payload et je peux le definir la-bas et je peux l'appeler

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findByEmailEquals(email, pageable).
                map(contactMessageMapper::contactMessageToResponse);

    }

    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());

        if(Objects.equals(type,"desc")){
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findBySubjectEquals(subject, pageable).
                map(contactMessageMapper::contactMessageToResponse);
    }

    public String deleteById(Long id) {

        getContactMessageById(id);
        contactMessageRepository.deleteById(id);
        return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;

    }

    public ContactMessage getContactMessageById(Long id) {
        return contactMessageRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));
    }

    public List<ContactMessage> searchBetweenDates(String beginDateString, String endDateString) {
        try {
            LocalDate begindate =  LocalDate.parse(beginDateString);
            LocalDate endDate = LocalDate.parse(endDateString);
            return contactMessageRepository.findMessagesBetweenDates(begindate, endDate);
        } catch (DateTimeParseException e) {
            throw new ConflictException(Messages.WRONG_DATE_MESSAGE);
        }
    }


    public List<ContactMessage> searchBetweenTimes(String startHourString, String startMinuteString, String endHourString, String endMinuteString) {

        try {
            int startHour = Integer.parseInt(startHourString);
            int startMinute = Integer.parseInt(startHourString);
            int endHour = Integer.parseInt(endHourString);
            int endMinute = Integer.parseInt(endHourString);

            return contactMessageRepository.findMessagesBetweenTimes(startHour, startMinute, endHour, endMinute);
        } catch (NumberFormatException e) {
            throw new ConflictException(Messages.WRONG_TIME_MESSAGE);
        }

    }
}
