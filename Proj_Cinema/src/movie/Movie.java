package movie;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;

/**
 * 영화 객체: id, title, genre 및 파일 입출력 기능
 */
public class Movie {
    private long id;            // 영화 대푯값 (epoch second)
    private String title;       // 제목
    private String genre;       // 장르

    // movies.txt 파일 객체 (프로젝트 실행 디렉토리에 위치)
    private static final File file = new File("movies.txt");

    // 생성자 (id 포함) - 내부에서 사용
    public Movie(long id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }

    // 생성자(신규 등록용) - id는 현재 시각 epoch second로 설정
    public Movie(String title, String genre) {
        this.id = Instant.now().getEpochSecond();
        this.title = title;
        this.genre = genre;
    }

    // 모든 영화 읽어오기 (파일이 없으면 빈 리스트 반환)
    public static ArrayList<Movie> findAll() throws IOException {
        ArrayList<Movie> movies = new ArrayList<>();
        // 파일이 없으면 빈 파일 생성
        if (!file.exists()) {
            file.createNewFile();
            return movies;
        }

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty()) continue;
            // 형식: id,title,genre
            String[] tokens = line.split(",", 3);
            if (tokens.length < 3) continue;
            long id;
            try {
                id = Long.parseLong(tokens[0]);
            } catch (NumberFormatException nfe) {
                continue;
            }
            String title = tokens[1];
            String genre = tokens[2];
            movies.add(new Movie(id, title, genre));
        }
        br.close();
        return movies;
    }

    @Override
    public String toString() {
        return String.format("id:%d, 제목:%s, 장르:%s", id, title, genre);
    }

    // 파일에 영화 정보 추가 (append)
    public void save() throws IOException {
        if (!file.exists()) file.createNewFile();
        FileWriter fw = new FileWriter(file, true);
        fw.write(this.toFileString() + "\n");
        fw.close();
    }

    // 객체를 파일 저장 형식으로 변환
    private String toFileString() {
        return String.format("%d,%s,%s", id, title, genre);
    }

    // 영화 삭제: id 문자열과 같은 행을 제거
    public static void delete(String movieIdStr) throws IOException {
        if (!file.exists()) return;
        File tempFile = new File("movies_temp.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        FileWriter fw = new FileWriter(tempFile, false); // 덮어쓰기

        String line;
        while ((line = br.readLine()) != null) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] tokens = trimmed.split(",", 3);
            if (tokens.length < 1) continue;
            if (tokens[0].equals(movieIdStr)) {
                // 삭제 대상이므로 이 행은 쓰지 않음
                continue;
            } else {
                fw.write(line + "\n");
            }
        }
        br.close();
        fw.close();

        // 원본 파일을 삭제하고 임시파일을 원래 이름으로 대체
        if (!file.delete()) {
            throw new IOException("원본 파일 삭제 실패");
        }
        if (!tempFile.renameTo(file)) {
            throw new IOException("임시 파일 이름 변경 실패");
        }
    }

    // 영화 조회: id로 찾아 Movie 객체 반환 (없으면 null)
    public static Movie findById(String movieIdStr) throws IOException {
        if (!file.exists()) return null;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Movie movie = null;
        while ((line = br.readLine()) != null) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] tokens = trimmed.split(",", 3);
            if (tokens.length < 3) continue;
            if (tokens[0].equals(movieIdStr)) {
                long id;
                try {
                    id = Long.parseLong(tokens[0]);
                } catch (NumberFormatException nfe) {
                    continue;
                }
                movie = new Movie(id, tokens[1], tokens[2]);
                break;
            }
        }
        br.close();
        return movie;
    }

    // 게터
    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }
}
