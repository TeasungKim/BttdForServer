package com.finalproject.bttd.apicontroller;

import com.finalproject.bttd.Utils.FileUpload;
import com.finalproject.bttd.apiresponse.ApiResponse;
import com.finalproject.bttd.apiresponse.PostNotFoundException;
import com.finalproject.bttd.dto.*;
import com.finalproject.bttd.entity.Board;
import com.finalproject.bttd.entity.Comment;
import com.finalproject.bttd.entity.User;
import com.finalproject.bttd.ClassMapper.BoardMapper;
import com.finalproject.bttd.ClassMapper.CommentMapper;
import com.finalproject.bttd.mybatismapper.BoardBatisMapper;
import com.finalproject.bttd.mybatismapper.UserBatisMapper;
import com.finalproject.bttd.repository.BoardRepository;
import com.finalproject.bttd.repository.CommentRepository;
import com.finalproject.bttd.repository.UserRepository;
import com.finalproject.bttd.security.CustomUserDetailService;
import com.finalproject.bttd.security.JWTGenerator;
import com.finalproject.bttd.service.BoardService;
import com.finalproject.bttd.service.CommentService;
import com.finalproject.bttd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.finalproject.bttd.apiresponse.ApiResponse.*;


@Slf4j
@RestController
public class ApiUserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTGenerator jwtGenerator;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomUserDetailService customUserDetailService;
    @Autowired
    private BoardService boardService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private FileUpload fileUpload;

    @Autowired
    private BoardBatisMapper boardBatisMapper;
    @Autowired
    private UserBatisMapper userBatisMapper;

    @PostMapping("/api/user")
    public ResponseEntity<ApiResponse<String>> createUser(@Valid @RequestBody User user, HttpServletRequest request) {
    try{

        User created = userService.create(user, request);
        ApiResponse<String> response = new ApiResponse<>();

        if(created != null){
        response.setStatus(SUCCESS_STATUS);
        response.setMessage("Success");
        response.setData(null);

        return ResponseEntity.ok(response);}
            else{
            response.setStatus(FAIL_STATUS);
            response.setMessage("please check the email");
            response.setData(null);

        }
    }
        catch(Exception ex){
            ex.printStackTrace();
            ApiResponse<String> errorResponse = new ApiResponse<>();
            errorResponse.setStatus(ERROR_STATUS); // ERROR_STATUS는 오류 상태를 나타내는 상수여야 함
            errorResponse.setMessage("중복된 아이디 값");
            errorResponse.setData(null);

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    return null;
    }

    @PostMapping("/api/emailTrue")
    public ResponseEntity<ApiResponse<String>> emailTrue (@RequestBody EmailDto emailDto){
     String user_id = emailDto.getUser_id();
      Optional<User> newUserId = userRepository.findByuser_id(user_id);
        log.info("newUserId ispresent : " + newUserId.isPresent());
        log.info("newUserID : " + newUserId);
        if (!newUserId.isPresent()){
            User newUser = new User();
            newUser.setUser_id(user_id);
            userRepository.save(newUser);

            ApiResponse<String> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("enable e-mail address");
            response.setData(null);

            return ResponseEntity.ok(response);
        } else {
            ApiResponse<String> response = new ApiResponse<>();
            response.setStatus(FAIL_STATUS);
            response.setMessage("allReady have e-mail address");
            response.setData(null);

            return ResponseEntity.ok(response);
        }

    }


    @PostMapping("/api/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>> login(@RequestBody LoginDto loginDto){
        log.info("1 : "+ loginDto.getUser_id());
        try {
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUser_id(), loginDto.getUser_password()));
            context.setAuthentication(authentication);
            SecurityContextHolder.setContext(context);
            //   SecurityContextHolder.getContext().setAuthentication(authentication);
            TokenDto token = jwtGenerator.generateToken(authentication);
            // TokenDto tokenDto = jwtGenerator.generateToken(authentication);

            AuthResponseDto authResponseDto = new AuthResponseDto(token.getAccessToken(), token.getRefreshToken());

            ApiResponse<AuthResponseDto> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("Success");
            response.setData(authResponseDto);

            return ResponseEntity.ok(response);

        }  catch (UsernameNotFoundException e) {
            ApiResponse<AuthResponseDto> errorResponse = new ApiResponse<>("Error", null, "존재하지 않는 아이디값");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (BadCredentialsException e) {
            ApiResponse<AuthResponseDto> errorResponse = new ApiResponse<>("Error", null, "비밀번호가 다릅니다");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<AuthResponseDto> errorResponse = new ApiResponse<>("Error", null, "존재하지 않는 아이디값");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        }

        }

 @GetMapping("/api/reissue")
    public ResponseEntity<ApiResponse<AuthResponseDto>> reIssue(Principal principal){
     log.info("reissue first 1 : "+ principal.getName());
      String user_name = principal.getName();

        userRepository.findByuser_id(user_name);
        if (user_name != null){
            UserDetails userDetails = customUserDetailService.loadUserByUsername(user_name);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            TokenDto token = jwtGenerator.generateToken(authenticationToken);

            AuthResponseDto authResponseDto = new AuthResponseDto(token.getAccessToken(), token.getRefreshToken());

            ApiResponse<AuthResponseDto> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("userId : " + user_name);
            response.setData(authResponseDto);

            return ResponseEntity.ok(response);
        } else {
            ApiResponse<AuthResponseDto> errorResponse = new ApiResponse<>();
            errorResponse.setStatus(ERROR_STATUS);
            errorResponse.setMessage("해당하는 토큰값이 존재하지 않습니다");
            errorResponse.setData(null);

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
 }

// 매칭글을 올리면 matching_post db생성 ,

    @PostMapping("/api/boardwrite")
    public ResponseEntity<ApiResponse<String>> boardwrite(@RequestBody BoardDto boardDto, Principal principal){


        String username = principal.getName();
        Optional<User> userOptional = userRepository.findByuser_id(username);

        if (!userOptional.isPresent()) {

            ApiResponse<String> errorResponse = new ApiResponse<>();
            errorResponse.setStatus(ERROR_STATUS);
            errorResponse.setMessage("해당하는 아이디값이 없습니다");
            errorResponse.setData(null);

            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }

        User user = userOptional.get();
        boardDto.setUser_id(user); // BoardDto에 User 객체를 직접 설정
        Board created = boardService.boardwrite(boardDto);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(SUCCESS_STATUS);
        response.setMessage("Success");
        response.setData(null);

        return ResponseEntity.ok(response);
    }

// 댓글이 달리면 matching_post의 post id 값을 가져와서 request_form 생성
    @PostMapping("/api/comment")
    public ResponseEntity<ApiResponse<String>> comments(@RequestBody CommentDto commentDto){
        log.info("commentDto : "+ commentDto);
        log.info("post id : " + commentDto.getPost_id());
        if (commentDto.getPost_id() == null) {
            ApiResponse<String> errorResponse = new ApiResponse<>();
            errorResponse.setStatus(ERROR_STATUS);
            errorResponse.setMessage("해당 게시글이 없습니다");
            errorResponse.setData(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        try {
           Comment created = commentService.comments(commentDto);

           ApiResponse<String> response = new ApiResponse<>();
           response.setStatus(SUCCESS_STATUS);
           response.setMessage("Success");
           response.setData(null);

           return ResponseEntity.ok(response);
       } catch (PostNotFoundException e){
           ApiResponse<String> errorResponse = new ApiResponse<>();
           errorResponse.setStatus(ERROR_STATUS);
           errorResponse.setMessage("해당 게시글이 없습니다");
           errorResponse.setData(null);

           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

       }
    }

// matching_post의 유저가 컨펌을 하면 request_form의 유저아이디 mathicng_post의 away_id에 저장

    @PostMapping("/api/commentConfirm")
    public ResponseEntity<ApiResponse<String>> commentConfirm(@RequestBody CommentConfirmDto commentConfirmDto){
        int postId = commentConfirmDto.getPost_id();
        String userId = commentConfirmDto.getRequest_user_id();

        Board board = boardRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당게시글이 없습니다."));
        log.info("is this real board? :" + board);
        board.setAway_id(userId);
        boardRepository.save(board);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(SUCCESS_STATUS);
        response.setMessage("Success");
        response.setData(null);

        return ResponseEntity.ok(response);

    }

// 이후 승패가 났을때 score에 점수저장 해당 점수 user에 user_win, user_lose 디비저장



    @PostMapping("/api/score")
    @Transactional
public ResponseEntity<ApiResponse<String>> score(@RequestBody ScoreDto scoreDto){
        int post_id = scoreDto.getPost_id();
        boolean score = scoreDto.isScore();
        score = true;


        Board board = boardRepository.findById(post_id).orElse(null); //게시글 번호 가져와서 조회
        String home_id = board.getUser_id().getUser_id(); // 홈아이디 가져옴
        String away_id = board.getAway_id(); // 어웨이 아이디 가져옴
        User user = userRepository.findByuser_id(home_id).orElse(null);  //유저겍체를 홈아이디로 찾아옴
        User awayUser = userRepository.findByuser_id(away_id).orElse(null); // 유저객체를 어웨이아이디로 찾아옴
        if(score == false){
            //boardDto 의 user_id 를 찾아서 해당 user_id의 user_lose 1증가
            user.setUser_lose(user.getUser_lose() + 1);
            awayUser.setUser_win(awayUser.getUser_win() + 1);
            //boardDto 의 away_id 를 찾아서 해당 away_id의 user_win 1증가
        } else {
            //boardDto 의 user_id 를 찾아서 해당 user_id의 user_win 1증가
            user.setUser_win(user.getUser_win() + 1);
            awayUser.setUser_lose(user.getUser_lose() + 1);
            //boardDto 의 away_id 를 찾아서 해당 away_id의 user_lose 1증가
        }
            //이후 가져온 값들 각각 user의 win lose에 추가

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(SUCCESS_STATUS);
        response.setMessage("Success");
        response.setData(null);


        return ResponseEntity.ok(response);
}

        @GetMapping("/api/getAllBoard")
        public ResponseEntity<ApiResponse<List<Boards>>> getAllBaord(){

       List<Board> board = boardBatisMapper.getWithComment();
       log.info("board info : "+ board);
            List<Boards> boardsDtoList = board.stream()
                    .map(BoardMapper::toDto)
                    .collect(Collectors.toList());

        log.info("boardsDtoList : " + boardsDtoList);

            ApiResponse<List<Boards>> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("Success");
            response.setData(boardsDtoList);
        return ResponseEntity.ok(response);
        }


        @GetMapping("/api/getDetailBoard")
        public ResponseEntity<ApiResponse<List<Comments>>> getAllComment(@RequestParam int post_id){

        log.info("getDetailBoard : success");

       List<Comments> commentList =  boardBatisMapper.findByPostUser(post_id);
       List<Comment> dge = commentRepository.findAllByPostId(post_id);

            log.info("commentList starts here now : " + commentList);

            List<CommentDetails> commentDetailsList1 = new ArrayList<>();
            for (Comment comment : dge) {
                CommentDetails details = CommentMapper.toDto(comment);
                log.info("commentDetailsList comment information : " + comment);
                log.info("commentDetailsList comment2 information : " + comment.getRequest_user_id());
                User user = userRepository.findByuser_id(comment.getRequest_user_id()).orElse(null);
                log.info("commentDetailsList user information : " + user);
                log.info("commentDetailsList photo information : " + user.getPhoto());
                if (user != null) {
                    details.setPhoto(user.getPhoto()); // CommentDetails DTO에 photo 정보 설정
                }
                commentDetailsList1.add(details);
            }



//
//            List<CommentDetails> commentDetailsList = dge.stream()
//                    .map(CommentMapper::toDto)
//                    .collect(Collectors.toList());
            commentList.forEach(comments -> comments.setComments(commentDetailsList1));

            ApiResponse<List<Comments>> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("Success");
            response.setData(commentList);
            return ResponseEntity.ok(response);
        }


        @PostMapping("/api/reUser")
        public ResponseEntity<ApiResponse<String>> reUser(@RequestBody User user, Principal principal){
        String userId = principal.getName();
            User reUser = userService.reUser(user, userId);
            ApiResponse<String> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("Success");
            response.setData(null);

            return ResponseEntity.ok(response);
        }

        @PostMapping("/api/accessButton")
        public  ResponseEntity<ApiResponse<String>> accessButton (@RequestBody BoardDto boardDto){
           int post_id = boardDto.getPost_id();
            Board newBoard = boardService.accessButton(post_id);
            String awayId = newBoard.getAway_id();

            ApiResponse<String> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("Success");
            response.setData(awayId);

            return ResponseEntity.ok(response);
        }

        @GetMapping("/api/userBoardList")
        public ResponseEntity<ApiResponse<List<Board>>> userBoardList(Principal principal){
        String user_id = principal.getName();
        log.info("userBoardList user_id : " + user_id);
        List<Board> board = boardBatisMapper.getUserBoard(user_id);
        log.info("board : " + board);


            ApiResponse<List<Board>> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("Success");
            response.setData(board);

            return ResponseEntity.ok(response);
        }

        @GetMapping("/api/userEmailConfirm")
        public ResponseEntity<ApiResponse<String>> userEmailConfirm(@RequestParam String user_id){

            Optional<User> user = userRepository.findByuser_id(user_id);
            if(user.isEmpty()){
                ApiResponse<String> response = new ApiResponse<>();
                response.setStatus(SUCCESS_STATUS);
                response.setMessage("Please proceed with email verification first");
                response.setData(null);
                return ResponseEntity.ok(response);

            }

            boolean enabled = user.get().isEnabled();
            if(enabled){
                ApiResponse<String> response = new ApiResponse<>();
                response.setStatus(SUCCESS_STATUS);
                response.setMessage("valid email address");
                response.setData(null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<String> response = new ApiResponse<>();
                response.setStatus(FAIL_STATUS);
                response.setMessage("Please check the email address");
                response.setData(null);
                return ResponseEntity.ok(response);
            }


        }

        @GetMapping("/api/userDetails")
    public ResponseEntity<ApiResponse<UserDetailDto>> userDetails(@RequestParam String user_id){


            UserDetailDto user = userBatisMapper.getUserDetails(user_id);

            ApiResponse<UserDetailDto> response = new ApiResponse<>();
            response.setStatus(SUCCESS_STATUS);
            response.setMessage("success");
            response.setData(user);
            return ResponseEntity.ok(response);}


 //토큰값이 true인지, false인지 확인하는 api만들기

    @GetMapping("/api/tokenEnable")
    public ResponseEntity<ApiResponse<String>> tokenEnable(@RequestBody EnableTokenDto enableTokenDto){

        String newToken = enableTokenDto.getToken();
        log.info("is token real : " + newToken);
        boolean isValid = jwtGenerator.validateToken(newToken);
        log.info("isValid : " + isValid);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(SUCCESS_STATUS);
        response.setMessage("success");
        response.setData(String.valueOf(isValid));
        return ResponseEntity.ok(response);

    }

    @GetMapping("/api/getBoardListPage")
    public ResponseEntity<ApiResponse<String>> getBoardListPage(@RequestParam int pageNum){

        log.info("paging starts 1 : " + pageNum);
        int limitNum = ((pageNum - 1) * 10);
        log.info("paging starts 2 : " + limitNum);
        List<Board> board = boardService.getBoardListPage(limitNum);
        log.info("paging starts 5 : " + board);

        ApiResponse<String> response = new ApiResponse<>();
        response.setStatus(SUCCESS_STATUS);
        response.setMessage("success");
        response.setData(board.toString());
        return ResponseEntity.ok(response);
    }

//
}
