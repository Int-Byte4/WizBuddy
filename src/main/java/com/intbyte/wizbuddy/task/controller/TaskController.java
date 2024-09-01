package com.intbyte.wizbuddy.task.controller;


import com.intbyte.wizbuddy.task.dto.TaskDTO;
import com.intbyte.wizbuddy.task.service.TaskService;
import com.intbyte.wizbuddy.task.vo.response.ResponseFindTaskVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final ModelMapper modelMapper;

    @Autowired
    public TaskController(TaskService taskService, ModelMapper modelMapper) {
        this.taskService = taskService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("task/{taskCode}")
    public ResponseEntity<ResponseFindTaskVO> getTask(@PathVariable("taskCode") int taskCode){

        TaskDTO taskDTO = taskService.findTaskById(taskCode);

        // 생각해보니까 자기 매장꺼만 알아야하네?
        // -> 근데 이건 ㄱㅊ을듯. 해당 task를 누르면 그 task의 code를 통해서 접속할테니

        ResponseFindTaskVO findTask = modelMapper.map(taskDTO, ResponseFindTaskVO.class);
        // modelMapper는 내부적으로 setter로 매핑하는데 setter가 vo에는 없잖아... 일단 ㅇㅋ

        return ResponseEntity.status(HttpStatus.OK).body(findTask);
    }

    // 이전에 자기 매장에 있는 모든 업무 조회하는 과정이 좀 힘드네
    // task 테이블에는 매장 번호가 없으니까 결국 join을 해서 매장을 가지고와야함.
    // 그렇게 하려면 1. 체크리스트별 업무 테이블과 조인하고 그걸 체크리스트와 조인?
    // 아닌듯 그냥 taskperchecklist에서 join 한번을 통해서 가져오면 될거같을거 같기도?


    @PostMapping("task")
    public ResponseEntity<ResponseFindTaskVO> insertTask(){

        return null;
    }


}

//1. 요청으로 보내는거하고 응답으로 받는거 차이를 생각하기

//-> user에서 보면 요청과 응답의 클래스 객체는 다른데 내부 구성요소는 동일하다.
//
//        2. vo에는 setter가 없다. -> vo가 사용자가 보내주는 데이터?
//        -> 사용자가 보내는 데이터는 변화x(불변)이기 때문에 setter를 적어주지 않는다.


//        3. 일단 task에서 어떤 요청이 들어오는거지?
//        -> 1. task 등록하는 요청 들어옴.   -> PostMapping("/task")
//        -> 2. task 조회하는 요청이 들어옴.  -> GetMapping("/task")
//
//        -> 3. task를 체크리스트에 등록하는 요청이 들어옴. == 이거는 taskperchecklist에서 진행해야할듯
//
//        -> 3. task 삭제하는 요청이 들어옴.
//        -> 4. task를 고정시키는 요청이 들어옴.
//        (3, 4번은 통째로 수정으로 처리하면 될듯) -> 수정은 how?
//
//        위 4개 요청이 들어올거같음.
//        아니다.
//        -> 5. task의 데이터를 바꾸는 요청이 들어옴. -> 이것도 수정이네? 그럼 345번을 모두 한번에 처리해줘야함.
