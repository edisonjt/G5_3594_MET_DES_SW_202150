/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prototipodecarnetdigital.controller;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 *
 * @author BryanPC
 */
public class EstudianteController {
    
    BasicDBObject document = new BasicDBObject();
    public DBObject request(String name, String id, String correo, String career, String address, String age) {
        document.put("Name", name);
        document.put("ID", id);
        document.put("Correo", correo);
        document.put("Career", career);
        document.put("Address", address);
        document.put("Age", age);
        return document;
    }
}
