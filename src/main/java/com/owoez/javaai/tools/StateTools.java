package com.owoez.javaai.tools;

import com.owoez.javaai.model.StateModel;
import com.owoez.javaai.persistence.State;
import com.owoez.javaai.persistence.StateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StateTools {
  private final StateRepository stateRepository;

  @Tool(description = "Gets the states currently in the db", name = "getStates")
  @Transactional(readOnly = true)
  public List<StateModel> getStates(){
    log.info("Getting states in the system");
    return stateRepository.findAll().stream().map(State::toModel).toList();
  }

  @Tool(description = "Create state with name")
  public StateModel createState(String name){
    State state = new State();
    state.setName(name);
    stateRepository.save(state);
    return state.toModel();
  }
}
