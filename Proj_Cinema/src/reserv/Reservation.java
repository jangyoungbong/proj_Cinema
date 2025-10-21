package reserv;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;

/**
 * 예매 객체: id(발급번호), movieId, movieTitle, seatName
 * 파일: reservations.txt (각 행: id,movieId,movieTitle,seatName)
 */
public class Reservation {
    private long id;            // 발급번호 (밀리초)
    private long movieId;       // 영화 대표값
    private String movieTitle;  // 영화 제목 (조회 편의성)
    private String seatName;    // 좌석명 (예: A-1)

    private static final File file = new File("reservations.txt");

    // 생성자(파일에서 읽을 때 사용)
    public Reservation(long id, long movieId, String movieTitle, String seatName) {
        this.id = id;
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.seatName = seatName;
    }

    // 생성자(신규 예매용)
    public Reservation(long movieId, String movieTitle, String seatName) {
        this.id = Instant.now().toEpochMilli(); // 밀리초
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.seatName = seatName;
    }

    // reservations.txt에서 id와 같은 예매를 찾아 객체로 반환
    public static Reservation findById(String reservationId) throws IOException {
        if (!file.exists()) return null;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        Reservation r = null;
        while ((line = br.readLine()) != null) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] tokens = trimmed.split(",", 4);
            if (tokens.length < 4) continue;
            if (tokens[0].equals(reservationId)) {
                long id = Long.parseLong(tokens[0]);
                long movieId = Long.parseLong(tokens[1]);
                String title = tokens[2];
                String seat = tokens[3];
                r = new Reservation(id, movieId, title, seat);
                break;
            }
        }
        br.close();
        return r;
    }

    @Override
    public String toString() {
        return String.format("영화: %s, 좌석: %s", movieTitle, seatName);
    }

    // 예매 삭제: id와 일치하는 행을 삭제하고 삭제된 Reservation 객체 반환
    public static Reservation cancel(String reservationId) throws IOException {
        if (!file.exists()) return null;
        File tempFile = new File("reservations_temp.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        FileWriter fw = new FileWriter(tempFile, false);

        String line;
        Reservation removed = null;
        while ((line = br.readLine()) != null) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] tokens = trimmed.split(",", 4);
            if (tokens.length < 4) continue;
            if (tokens[0].equals(reservationId)) {
                // 삭제 대상: 객체를 만들어 반환할 준비
                long id = Long.parseLong(tokens[0]);
                long movieId = Long.parseLong(tokens[1]);
                String title = tokens[2];
                String seat = tokens[3];
                removed = new Reservation(id, movieId, title, seat);
                // 이 행은 쓰지 않음 (삭제)
            } else {
                fw.write(line + "\n");
            }
        }
        br.close();
        fw.close();

        // 원본 삭제 및 대체
        if (!file.delete()) {
            throw new IOException("원본 파일 삭제 실패");
        }
        if (!tempFile.renameTo(file)) {
            throw new IOException("임시 파일 이름 변경 실패");
        }
        return removed;
    }

    // 특정 영화(movieIdStr)와 관련된 모든 예매 목록 반환
    public static ArrayList<Reservation> findByMovieId(String movieIdStr) throws IOException {
        ArrayList<Reservation> reservations = new ArrayList<>();
        if (!file.exists()) return reservations;
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) continue;
            String[] tokens = trimmed.split(",", 4);
            if (tokens.length < 4) continue;
            if (tokens[1].equals(movieIdStr)) {
                long id = Long.parseLong(tokens[0]);
                long movieId = Long.parseLong(tokens[1]);
                String title = tokens[2];
                String seat = tokens[3];
                reservations.add(new Reservation(id, movieId, title, seat));
            }
        }
        br.close();
        return reservations;
    }

    // 예매를 파일에 저장 (append)
    public void save() throws IOException {
        if (!file.exists()) file.createNewFile();
        FileWriter fw = new FileWriter(file, true);
        fw.write(this.toFileString() + "\n");
        fw.close();
    }

    // 파일 저장 형식 문자열
    private String toFileString() {
        return String.format("%d,%d,%s,%s", id, movieId, movieTitle, seatName);
    }

    // 게터
    public long getId() {
        return id;
    }

    public String getSeatName() {
        return seatName;
    }

    public long getMovieId() {
        return movieId;
    }
}