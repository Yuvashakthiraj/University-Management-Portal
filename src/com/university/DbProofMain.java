package com.university;

import com.university.service.DatabaseInspectorService;
import com.university.service.SchemaInitializer;

public class DbProofMain {
    public static void main(String[] args) {
        try {
            new SchemaInitializer().initialize();
            DatabaseInspectorService inspector = new DatabaseInspectorService();
            System.out.println(inspector.getDatabaseProofReport());
            System.out.println("Table Structures");
            System.out.println("----------------");
            System.out.println(inspector.getTableStructureReport());
        } catch (Exception ex) {
            System.out.println("Failed to inspect database: " + ex.getMessage());
        }
    }
}
