package menu;

import java.io.IOException;
import java.util.ArrayList;
import reserv.Reservation;
import movie.Movie;
import movie.Seats;

/**
 * 사용자용 메인 메뉴 (Singleton)
 */
public class MainMenu extends AbstractMenu {
    // 싱글톤 인스턴스
    private static final MainMenu instance = new MainMenu(null);

    // 메인 메뉴 텍스트
    private static final String MAIN_MENU_TEXT =
            "1: 영화 예매하기\n" +
            "2: 예매 확인하기\n" +
            "3: 예매 취소하기\n" +
            "4: 관리자 메뉴로 이동\n" +
            "q: 종료\n\n" +
            "메뉴를 선택하세요: ";

    // private 생성자 (Singleton)
    private MainMenu(Menu prevMenu) {
        super(MAIN_MENU_TEXT, prevMenu);
    }

    // 인스턴스 반환
    public static MainMenu getInstance() {
        return instance;
    }

    // 다음 메뉴 결정 및 핵심 로직 처리
    @Override
    public Menu next() {
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1": // 영화 예매
                reserve();
                return this; // 완료 후 다시 메인메뉴
            case "2": // 예매 확인
                checkReservation();
                return this;
            case "3": // 예매 취소
                cancelReservation();
                return this;
            case "4": // 관리자 메뉴 이동 (비밀번호 확인 후 이동)
                AdminMenu adminMenu = AdminMenu.getInstance();
                // 현재 메뉴를 admin의 prev로 설정
                adminMenu.setPrevMenu(this);
                if (checkAdminPassword()) {
                    return adminMenu;
                } else {
                    System.out.println(">> 비밀번호가 틀렸습니다.");
                    return this;
                }
            case "q": // 종료: prevMenu가 null이면 프로그램 종료 루프가 끝남
                return prevMenu;
            default:
                System.out.println(">> 잘못된 입력입니다.");
                return this;
        }
    }

    // (1) 영화 예매 기능
    private void reserve() {
        try {
            // 모든 영화 목록 읽기
            ArrayList<Movie> movies = Movie.findAll();
            if (movies.isEmpty()) {
                System.out.println(">> 등록된 영화가 없습니다.");
                return;
            }

            // 영화 목록 출력 (번호로 선택)
            System.out.println("=== 영화 목록 ===");
            for (int i = 0; i < movies.size(); i++) {
                System.out.printf("%d: %s (%s)\n", i + 1, movies.get(i).getTitle(), movies.get(i).getGenre());
            }
            System.out.print("예매할 영화를 선택하세요: ");
            String movieChoice = scanner.nextLine().trim();
            int idx;
            try {
                idx = Integer.parseInt(movieChoice) - 1;
            } catch (NumberFormatException nfe) {
                System.out.println(">> 잘못된 입력입니다.");
                return;
            }
            if (idx < 0 || idx >= movies.size()) {
                System.out.println(">> 선택 범위를 벗어났습니다.");
                return;
            }
            Movie chosen = movies.get(idx);

            // 해당 영화의 예매(예약) 목록을 불러와 좌석표를 표시
            ArrayList<Reservation> reservations = Reservation.findByMovieId(String.valueOf(chosen.getId()));
            Seats seats = new Seats(reservations);
            seats.show();

            // 좌석 선택
            System.out.print("좌석을 선택하세요(예: A-1): ");
            String seat = scanner.nextLine().trim().toUpperCase();

            // 좌석 예약 표시(중복 예외 발생 가능)
            seats.mark(seat);

            // 예매 객체 생성 및 저장
            Reservation r = new Reservation(chosen.getId(), chosen.getTitle(), seat);
            r.save();

            System.out.println(">> 예매가 완료되었습니다.");
            System.out.printf(">> 발급번호: %d\n", r.getId());
        } catch (IOException e) {
            System.out.println(">> 파일 입출력에 문제가 생겼습니다..");
        } catch (Exception e) {
            System.out.printf(">> 예매에 실패하였습니다: %s\n", e.getMessage());
        }
    }

    // (2) 예매 확인
    private void checkReservation() {
        System.out.print("발급번호를 입력하세요: ");
        String idStr = scanner.nextLine().trim();
        try {
            Reservation r = Reservation.findById(idStr);
            if (r != null) {
                System.out.printf(">> [확인 완료] %s\n", r.toString());
            } else {
                System.out.println(">> 예매 내역이 없습니다.");
            }
        } catch (IOException e) {
            System.out.println(">> 파일 입출력에 문제가 생겼습니다.");
        }
    }

    // (3) 예매 취소
    private void cancelReservation() {
        System.out.print("발급번호를 입력하세요: ");
        String idStr = scanner.nextLine().trim();
        try {
            Reservation r = Reservation.cancel(idStr);
            if (r != null) {
                System.out.printf(">> 취소가 완료되었습니다: %s\n", r.toString());
            } else {
                System.out.println(">> 취소할 예매가 없습니다.");
            }
        } catch (IOException e) {
            System.out.println(">> 파일 입출력에 문제가 생겼습니다.");
        }
    }

    // (4) 관리자 비밀번호 확인 (간단한 텍스트 비밀번호)
    private boolean checkAdminPassword() {
        final String ADMIN_PASSWORD = "admin1234"; // 실제 운영시 안전하게 변경 필요
        System.out.print("관리자 비밀번호를 입력하세요: ");
        String input = scanner.nextLine().trim();
        return ADMIN_PASSWORD.equals(input);
    }
}