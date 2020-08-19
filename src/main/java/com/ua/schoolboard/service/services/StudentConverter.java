package com.ua.schoolboard.service.services;

import com.ua.schoolboard.rest.model.UpdateStudentTO;
import com.ua.schoolboard.rest.model.UpdateTeacherTO;
import com.ua.schoolboard.rest.model.UserTO;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StudentConverter implements Converter<String, UserTO> {
    private final UserService userService;
    private  final GroupService groupService;



    /*  @Component
      public class DrinkConverter implements Converter<String, Drink> {
          @Override
          public Drink convert(String id) {
              System.out.println("Trying to convert id=" + id + " into a drink");

              int parsedId = Integer.parseInt(id);
              List<Drink> selectableDrinks = Arrays.asList(
                      new Drink(1L, "coke"),
                      new Drink(2L, "fanta"),
                      new Drink(3L, "sprite")
              );
              int index = parsedId - 1;
              return selectableDrinks.get(index);
          }
      }*/


    @Override
    public UpdateStudentTO convert(String id) {
        Long userId = Long.parseLong(id);
        return this.userService.getStudent(userId);
    }
}

