import { useCallback, useEffect, useRef } from "react";

export interface IndexedDBConfig {
  dbName: string;
  version: number;
  storeName: string;
  keyPath?: string;
  autoIncrement?: boolean;
  indexes?: {
    indexName: string;
    keyPath: string | string[];
    options?: IDBIndexParameters;
  }[];
}

export function useIndexedDB<T>(config: IndexedDBConfig) {
  const dbRef = useRef<IDBDatabase | null>(null);

  const getDB = useCallback(async (): Promise<IDBDatabase> => {
    if (dbRef.current) return dbRef.current;

    return await new Promise<IDBDatabase>((resolve, reject) => {
      const request = indexedDB.open(config.dbName, config.version);

      request.onupgradeneeded = () => {
        const db = request.result;
        if (!db.objectStoreNames.contains(config.storeName)) {
          const store = db.createObjectStore(config.storeName, {
            keyPath: "id",
          });
          store.createIndex("key_user", ["user_id"], {
            unique: false,
          });
        }
      };

      request.onsuccess = () => {
        dbRef.current = request.result;
        resolve(request.result);
      };

      request.onerror = () => {
        reject(request.error);
      };
    });
  }, [config]);

  useEffect(() => {
    getDB().catch((error) => console.error("Error initializing IndexedDB:", error));
    return () => {
      if (dbRef.current) {
        dbRef.current.close();
        dbRef.current = null;
      }
    };
  }, [getDB]);

  const addItem = useCallback(
    async (item: T) => {
      const db = await getDB();
      return new Promise<IDBValidKey>((resolve, reject) => {
        const transaction = db.transaction(config.storeName, "readwrite");
        const store = transaction.objectStore(config.storeName);
        const request = store.add(item);
        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
      });
    },
    [getDB, config.storeName]
  );

  const getItem = useCallback(
    async (key: IDBValidKey): Promise<T | undefined> => {
      const db = await getDB();
      return new Promise((resolve, reject) => {
        const transaction = db.transaction(config.storeName, "readonly");
        const store = transaction.objectStore(config.storeName);
        const request = store.get(key);
        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
      });
    },
    [getDB, config.storeName]
  );

  const getAllItems = useCallback(
    async (indexName?: string, query?: IDBKeyRange | IDBValidKey | null): Promise<T[]> => {
      const db = await getDB();
      return new Promise((resolve, reject) => {
        const transaction = db.transaction(config.storeName, "readonly");
        const store = transaction.objectStore(config.storeName);
        let request: IDBRequest;
        if (indexName) {
          const index = store.index(indexName);
          request = query ? index.getAll(query) : index.getAll();
        } else {
          request = query ? store.getAll(query) : store.getAll();
        }
        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
      });
    },
    [getDB, config.storeName]
  );

  const updateItem = useCallback(
    async (item: T) => {
      const db = await getDB();
      return new Promise<IDBValidKey>((resolve, reject) => {
        const transaction = db.transaction(config.storeName, "readwrite");
        const store = transaction.objectStore(config.storeName);
        const request = store.put(item);
        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
      });
    },
    [getDB, config.storeName]
  );

  const deleteItem = useCallback(
    async (key: IDBValidKey) => {
      const db = await getDB();
      return new Promise<IDBValidKey | undefined>((resolve, reject) => {
        const transaction = db.transaction(config.storeName, "readwrite");
        const store = transaction.objectStore(config.storeName);
        const request = store.delete(key);
        request.onsuccess = () => resolve(request.result);
        request.onerror = () => reject(request.error);
      });
    },
    [getDB, config.storeName]
  );

  const deleteAllItems = useCallback(
    async (indexName?: string, query?: IDBKeyRange | IDBValidKey | null) => {
      const db = await getDB();
      return new Promise((resolve, reject) => {
        const transaction = db.transaction(config.storeName, "readwrite");
        const store = transaction.objectStore(config.storeName);
        let requestCursor: IDBRequest;
        if (indexName) {
          const index = store.index(indexName);
          requestCursor = index.openCursor(query);
        } else {
          requestCursor = store.openCursor(query);
        }
        requestCursor.onsuccess = (event) => {
          const cursor = (event.target as IDBRequest).result;
          if (cursor) {
            cursor.delete();
            cursor.continue();
          } else {
            resolve(true);
          }
        };
        requestCursor.onerror = () => reject(requestCursor.error);
      });
    },
    [getDB, config.storeName]
  );

  return {
    addItem,
    getItem,
    getAllItems,
    updateItem,
    deleteItem,
    deleteAllItems,
  };
}
