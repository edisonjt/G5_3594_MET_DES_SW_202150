/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipodecarnetdigital.controller;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import ec.edu.espe.datamanager.utils.MongoDBManager;
import ec.edu.espe.datamanager.utils.NSQLDBManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author BryanPC
 */
public class TablasController {
    
    public DefaultTableModel tableStudent() {
        
        NSQLDBManager mongo = new MongoDBManager();
        mongo.openConection("Name");
        DB BaseData = MongoDBManager.getMongoC().getDB("PrototypeVirtualID");
        DBCollection collections = BaseData.getCollection("Name");
        DBCursor cursor = collections.find();

        String[] columnNames = {"Nombres", "ID", "Carrera",
            "Dirección", "Edad"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        while (cursor.hasNext()) {
            DBObject obj = cursor.next();
            String name = (String) obj.get("Name");
            String id = (String) obj.get("ID");
            String career = (String) obj.get("Career");
            String email = (String) obj.get("Address");
            String Address = (String) obj.get("Age").toString();
            model.addRow(new Object[]{name, id, career, email, Address});
        }
        return model;
    }
    
}
