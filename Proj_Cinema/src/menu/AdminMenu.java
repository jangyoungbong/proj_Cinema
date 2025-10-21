package menu;

import java.io.IOException;
import java.util.ArrayList;
import movie.Movie;

/**
 * 관리자 메뉴 (영화 등록/목록/삭제)
 */
public class AdminMenu extends AbstractMenu {
    private static final AdminMenu instance = new AdminMenu(null);

    private static final String ADMIN_MENU_TEXT =
            "1: 영화 등록하기\n" +
            "2: 영화 목록 보기\n" +
            "3: 영화 삭제하기\n" +
            "b: 메인 메뉴로 이동\n\n" +
            "메뉴를 선택하세요: ";

    private AdminMenu(Menu prevMenu) {
        super(ADMIN_MENU_TEXT, prevMenu);
    }

    public static AdminMenu getInstance() {
        return instance;
    }

    @Override
    public Menu next() {
        String input = scanner.nextLine().trim();
        switch (input) {
            case "1":
                createMovie();
                return this;
            case "2":
                printAllMovies();
                return this;
            case "3":
                deleteMovie();
                return this;
            case "b":
                return prevMenu; // 이전 메뉴(메인)로 복귀
            default:
                System.out.println(">> 잘못된 입력입니다.");
                return this;
        }
    }

    // (1) 영화 등록
    private void createMovie() {
        System.out.print("제목: ");
        String title = scanner.nextLine().trim();
        System.out.print("장르: ");
        String genre = scanner.nextLine().trim();

        Movie m = new Movie(title, genre);
        try {
            m.save();
            System.out.println(">> 등록되었습니다.");
        } catch (IOException e) {
            System.out.println(">> 실패하였습니다.");
        }
    }

    // (2) 모든 영화 출력
    private void printAllMovies() {
        try {
            ArrayList<Movie> movies = Movie.findAll();
            if (movies.isEmpty()) {
                System.out.println(">> 등록된 영화가 없습니다.");
                return;
            }
            System.out.println("=== 영화 목록 ===");
            for (Movie m : movies) {
                System.out.printf("%d : %s (%s)\n", m.getId(), m.getTitle(), m.getGenre());
            }
        } catch (IOException e) {
            System.out.println("데이터 접근에 실패하였습니다.");
        }
    }

    // (3) 영화 삭제
    private void deleteMovie() {
        try {
            ArrayList<Movie> movies = Movie.findAll();
            if (movies.isEmpty()) {
                System.out.println(">> 삭제할 영화가 없습니다.");
                return;
            }
            System.out.println("=== 영화 목록 ===");
            for (int i = 0; i < movies.size(); i++) {
                System.out.printf("%d: %s (%s) [id:%d]\n", i + 1, movies.get(i).getTitle(), movies.get(i).getGenre(), movies.get(i).getId());
            }
            System.out.print("삭제할 영화를 선택하세요: ");
            String choice = scanner.nextLine().trim();
            int idx;
            try {
                idx = Integer.parseInt(choice) - 1;
            } catch (NumberFormatException e) {
                System.out.println(">> 잘못된 입력입니다.");
                return;
            }
            if (idx < 0 || idx >= movies.size()) {
                System.out.println(">> 선택 범위를 벗어났습니다.");
                return;
            }
            String idStr = String.valueOf(movies.get(idx).getId());
            Movie.delete(idStr);
            System.out.println(">> 삭제되었습니다.");
        } catch (IOException e) {
            System.out.println(">> 삭제에 실패하였습니다.");
        }
    }
}