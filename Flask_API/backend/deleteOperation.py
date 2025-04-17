import sqlite3
from flask import jsonify

def deleteUser(userId):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        cursor.execute(
            '''
                DELETE FROM Users WHERE user_id = ?
            ''',
            (userId,)
        )
        conn.commit()
        conn.close()
        return jsonify({"message" : userId, "status" : 200})
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})

def deleteProduct(productId):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        cursor.execute(
            '''
                DELETE FROM Products WHERE product_id = ?
            ''',
            (productId,)
        )
        conn.commit()
        conn.close()
        return jsonify({"message" : productId, "status" : 200})
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})
    
def deleteOrderDetails(orderId):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        cursor.execute(
            '''
                DELETE FROM Order_Details WHERE order_id = ?
            ''',
            (orderId,)
        )
        conn.commit()
        conn.close()
        return jsonify({"message" : orderId, "status" : 200})
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})
    
def deleteSellHistory(sellHistoryId):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        cursor.execute(
            '''
                DELETE FROM Sell_History WHERE sell_history_id = ?
            ''',
            (sellHistoryId,)
        )
        conn.commit()
        conn.close()
        return jsonify({"message" : sellHistoryId, "status" : 200})
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})
    
def deleteAvailableProducts(productId):
    try:
        conn = sqlite3.connect("my_medicalShop.db")
        cursor = conn.cursor()
        cursor.execute(
            '''
                DELETE FROM Available_Products WHERE product_id = ?
            ''',
            (productId,)
        )
        conn.commit()
        conn.close()
        return jsonify({"message" : productId, "status" : 200})
    except Exception as error:
        return jsonify({"message" : error, "status" : 400})