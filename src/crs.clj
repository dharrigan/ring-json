(ns crs
  (:require
   [compojure.core :refer [defroutes POST]]
   [compojure.route :as route]
   [ring.adapter.jetty :as jetty]
   [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
   [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
   [ring.util.response :refer [response]]))

(defroutes app-routes
  (POST "/" {{:keys [msg]} :body} (response {:reply (str "Hello " msg "!!!!")}))
  (route/not-found "Not Found"))

(def app
  (-> (wrap-json-response app-routes)
      (wrap-json-body {:keywords? true})
      (wrap-defaults (assoc site-defaults :security false))))

(defn start-server
  []
  (jetty/run-jetty #'app {:port 3000 :join? false}))

(defn stop-server
  [server]
  (.stop server))

(comment

 (require '[crs :as c])

 (def server (c/start-server))

 (c/stop-server server)

 ,)
