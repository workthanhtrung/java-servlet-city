package dao;

import model.City;
import model.Nation;

import java.util.List;

public interface INationDAO {
    public List<Nation> selectAllNations();
}
