package com.ua.schoolboard.rest.model;


import com.ua.schoolboard.service.model.UserBO;

import java.util.Collections;
import java.util.function.Function;

public enum Role {
    STUDENT {
        @Override
        public UserTO newUser() {
            return new UpdateStudentTO();
        }

        @Override
        public void updateUser(GroupTO group, UserBO user, Function<UserBO, UpdateTeacherTO> teacherMapper, Function<UserBO, UpdateStudentTO> studentMapper) {
            UpdateStudentTO updateStudentTO = studentMapper.apply(user);
            if (!group.getStudents().isEmpty()) {
                group.getStudents().add(updateStudentTO);
            } else {
                group.setStudents(Collections.singletonList(updateStudentTO));
            }
        }

    },
    TEACHER {
        @Override
        public UserTO newUser() {
            return new UpdateTeacherTO();
        }

        @Override
        public void updateUser(GroupTO group, UserBO user, Function<UserBO, UpdateTeacherTO> teacherMapper, Function<UserBO, UpdateStudentTO> studentMapper) {
            UpdateTeacherTO updateTeacherTO = teacherMapper.apply(user);
            group.setTeacher(updateTeacherTO);
        }
    },
    ADMIN {
        @Override
        public UserTO newUser() {
            return new UpdateAdminTO();
        }

        @Override
        public void updateUser(GroupTO group, UserBO user, Function<UserBO, UpdateTeacherTO> teacherMapper, Function<UserBO, UpdateStudentTO> studentMapper) {
            //stub
        }
    };
 /*   SCHOOL{
        @Override
        public UserTO newUser() {
            return new School();
        }

        @Override
        public void updateUser(GroupTO group, UserBO user, Function<UserBO, UpdateTeacherTO> teacherMapper, Function<UserBO, UpdateStudentTO> studentMapper) {

        }
    };*/

    public abstract UserTO newUser();

    public abstract void updateUser(GroupTO group, UserBO user, Function<UserBO, UpdateTeacherTO> teacherMapper, Function<UserBO, UpdateStudentTO> studentMapper);
}
