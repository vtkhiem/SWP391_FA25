package dal;

import java.sql.*;
import java.time.*;
import java.util.HashSet;
import java.util.Set;
import model.Ban;

public class BanDAO extends DBContext {

    public void insertForCandidate(int candidateId, Instant bannedUntilUTC) throws SQLException {
        String sql = "INSERT INTO dbo.Ban (CandidateID, BannedUntil) VALUES (?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateId);
            if (bannedUntilUTC == null) {
                ps.setNull(2, Types.TIMESTAMP);
            } else {
                ps.setTimestamp(2, Timestamp.from(bannedUntilUTC));
            }
            ps.executeUpdate();
        }
    }

    public boolean isCandidateBannedNow(int candidateId) throws SQLException {
        String sql = """
            SELECT 1
            FROM dbo.Ban
            WHERE CandidateID = ?
              AND (BannedUntil IS NULL OR BannedUntil > SYSUTCDATETIME())
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Ban getActiveBanForCandidate(int candidateId) throws SQLException {
        String sql = """
            SELECT TOP 1 BanID, EmployerID, CandidateID, BannedAt, BannedUntil
            FROM dbo.Ban
            WHERE CandidateID = ?
              AND (BannedUntil IS NULL OR BannedUntil > SYSUTCDATETIME())
            ORDER BY BannedAt DESC
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Ban b = new Ban();
                    b.setBanId(rs.getInt("BanID"));
                    int empId = rs.getInt("EmployerID");
                    b.setEmployerId(rs.wasNull() ? null : empId);
                    int candId = rs.getInt("CandidateID");
                    b.setCandidateId(rs.wasNull() ? null : candId);

                    Timestamp at = rs.getTimestamp("BannedAt");
                    if (at != null) b.setBannedAt(at.toInstant());
                    Timestamp until = rs.getTimestamp("BannedUntil");
                    b.setBannedUntil(until == null ? null : until.toInstant());
                    return b;
                }
                return null;
            }
        }
    }

   
    public static Instant parseBangkokLocalToUTC(String datetimeLocal) {
        if (datetimeLocal == null || datetimeLocal.isBlank()) return null;
        LocalDateTime ldt = LocalDateTime.parse(datetimeLocal); 
        ZoneId bangkok = ZoneId.of("Asia/Bangkok");
        ZonedDateTime zdt = ldt.atZone(bangkok);
        return zdt.toInstant();
    }
      public Set<Integer> getActiveBannedCandidateIds() throws SQLException {
        String sql = """
            SELECT DISTINCT CandidateID
            FROM dbo.Ban
            WHERE CandidateID IS NOT NULL
              AND (BannedUntil IS NULL OR BannedUntil > SYSUTCDATETIME())
        """;
        Set<Integer> ids = new HashSet<>();
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt(1);
                if (!rs.wasNull()) ids.add(id);
            }
        }
        return ids;
    }

    public int unbanCandidateActive(int candidateId) throws SQLException {
        String sql = """
            DELETE FROM dbo.Ban
            WHERE CandidateID = ?
              AND (BannedUntil IS NULL OR BannedUntil > SYSUTCDATETIME())
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, candidateId);
            return ps.executeUpdate(); 
        }
    }
     public void insertForEmployer(int employerId, Instant bannedUntilUTC) throws SQLException {
        String sql = "INSERT INTO dbo.Ban (EmployerID, BannedUntil) VALUES (?, ?)";
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
            if (bannedUntilUTC == null) ps.setNull(2, Types.TIMESTAMP);
            else ps.setTimestamp(2, Timestamp.from(bannedUntilUTC));
            ps.executeUpdate();
        }
    }

    public Set<Integer> getActiveBannedEmployerIds() throws SQLException {
        String sql = """
            SELECT DISTINCT EmployerID
            FROM dbo.Ban
            WHERE EmployerID IS NOT NULL
              AND (BannedUntil IS NULL OR BannedUntil > SYSUTCDATETIME())
        """;
        Set<Integer> ids = new HashSet<>();
        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt(1);
                if (!rs.wasNull()) ids.add(id);
            }
        }
        return ids;
    }

    public int unbanEmployerActive(int employerId) throws SQLException {
        String sql = """
            DELETE FROM dbo.Ban
            WHERE EmployerID = ?
              AND (BannedUntil IS NULL OR BannedUntil > SYSUTCDATETIME())
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
            return ps.executeUpdate();
        }
    }

    public Ban getActiveBanForEmployer(int employerId) throws SQLException {
        String sql = """
            SELECT TOP 1 BanID, EmployerID, CandidateID, BannedAt, BannedUntil
            FROM dbo.Ban
            WHERE EmployerID = ?
              AND (BannedUntil IS NULL OR BannedUntil > SYSUTCDATETIME())
            ORDER BY BannedAt DESC
        """;
        try (PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, employerId);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                Ban b = new Ban();
                b.setBanId(rs.getInt("BanID"));
                int empId = rs.getInt("EmployerID");
                b.setEmployerId(rs.wasNull() ? null : empId);
                Timestamp at = rs.getTimestamp("BannedAt");
                if (at != null) b.setBannedAt(at.toInstant());
                Timestamp until = rs.getTimestamp("BannedUntil");
                b.setBannedUntil(until == null ? null : until.toInstant());
                return b;
            }
        }
    }

}
