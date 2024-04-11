package com.example.Active.Razgrad;

import com.example.Active.Razgrad.activity.ActivityService;
import com.example.Active.Razgrad.user.Role;
import com.example.Active.Razgrad.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private UserRepository userRepository;

//    @Test
//    @WithMockUser(roles = "ADMIN")////бърка го 6 ред от хедъра
//    void testAddActivity()throws Exception{
//
//        mockMvc.perform(get("/activity/add")).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("add-activity"))
//                .andExpect(model().attributeExists("activity"))
//                .andExpect(model().attributeExists("communityCreator"))
//                .andExpect(model().attributeExists("category"));
//
//    }

    @Test
    void testListActivities() throws Exception {
        mockMvc.perform(get("/activity/list")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("list-activities"))
                .andExpect(model().attributeExists("activity"));
    }

//    @Test
//    @WithMockUser(roles = "ADMIN")
//    void testEditActivity() throws Exception {//бърка го 6 ред от хедъра
//        mockMvc.perform(get("/activity/edit").param("id","1")).andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(view().name("edit-activity"))
//                .andExpect(model().attributeExists("activity"))
//                .andExpect(model().attributeExists("communityCreator"))
//                .andExpect(model().attributeExists("category"));
//    }
}
