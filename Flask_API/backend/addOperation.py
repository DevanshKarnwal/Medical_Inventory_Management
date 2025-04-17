from flask import jsonify
import sqlite3
import uuid
from datetime import date 

def createUser(name, password, phoneNumber, email, pincode, address):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        userId = str(uuid.uuid4())
        dateOfAccountCreation = date.today()
        cursor.execute(
            '''
                INSERT INTO Users (user_id, password, date_of_account_creation, isApproved, block, name, address, email, phoneNumber, pincode)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ''',
            (userId, password, dateOfAccountCreation, 0, 0, name, address, email, phoneNumber, pincode)
        )
    
        conn.commit()
        conn.close()
        return jsonify({"message" : userId, "status" : 200})
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})
    
    

# PRODUCT

def createProduct(name,price,category,stock):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        product_id = str(uuid.uuid4())
        cursor.execute(
            '''
                INSERT INTO Products (product_id, name, price, category, stock)
                VALUES (?, ?, ?, ?, ?)
            ''',
            (product_id, name, price, category, stock)
        )
    
        conn.commit()
        conn.close()
        return jsonify({"message" : product_id, "status" : 200})
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})
    
    
# ORDER DETAILS
def createOrderDetails(user_id,product_id,isApproved,quantity,price,total_amount,product_name,user_name,message,category):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        order_id = str(uuid.uuid4())
        date_of_order_creation = date.today()
        cursor.execute(
            '''
                INSERT INTO Order_Details (order_id, user_id, product_id, isApproved, quantity, date_of_order_creation, price, total_amount, product_name, user_name, message, category)
                VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
            ''', 
            (order_id, user_id, product_id, isApproved, quantity, date_of_order_creation, price, total_amount, product_name, user_name, message, category)
        )
        conn.commit()
        conn.close()
        return jsonify({"message" : order_id, "status" : 200})
        
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})
    
    
    
#Sell historty
def createSellHistory(product_id,quantity,remaning_stock,total_amount,price,product_name,user_id,user_name):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        sell_id = str(uuid.uuid4())
        date_of_sell = date.today()
        cursor.execute(
            '''
                INSERT INTO Sell_History (sell_id, product_id, quantity, remaning_stock, date_of_sell, price, total_amount, product_name, user_id, user_name)
                VALUES (?,?,?,?,?,?,?,?,?,?)
            ''', 
            (sell_id, product_id, quantity, remaning_stock, date_of_sell, price, total_amount, product_name, user_id, user_name)
        )
        conn.commit()
        conn.close()
        return jsonify({"message" : sell_id , "status" : 200})
        
    except Exception as error:
        return jsonify({"message" : error , "status" : 400})
    
def createAvailableProducts(name,price,category,stock,user_id,user_name):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        product_id = str(uuid.uuid4())
        cursor.execute(
            '''
                INSERT INTO Available_Products (product_id, name, price, category, stock, user_id, user_name)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            ''',
            (product_id, name, price, category, stock, user_id, user_name)
        )
    
        conn.commit()
        conn.close()
        return jsonify({"message" : product_id , "status" : 200})
        
    except Exception as error:
        return jsonify({"message" : error , "status" : 400})