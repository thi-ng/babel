(ns leiningen.new.thing-babel
  (:require [leiningen.new.templates :as tpl]
            [leiningen.core.main :as main]
            [clojure.string :as str]))

(def licenses
  {"asl" {:name "Apache Software License 2.0" :url "http://www.apache.org/licenses/LICENSE-2.0"}
   "epl" {:name "Eclipse Public License" :url "http://www.eclipse.org/legal/epl-v10.html"}})

(def render (tpl/renderer "thing-babel"))

(defn group-name
  "Replace hyphens with underscores."
  [^String s]
  (let [idx (.indexOf s "/")]
    (when (pos? idx)
      (subs s 0 idx))))

(defn opts-info
  [opts ks]
  (doseq [[k desc] (partition 2 ks)]
    (main/info (format "%-24s: %s" desc (str (opts k))))))

(defn thing-babel
  "Literal programming template for org-mode babel projects"
  [name & args]
  (let [opts (->> args
                  (partition 2)
                  (map #(vector (keyword (first %)) (second %)))
                  (into {}))
        {:keys [author license target url]
         :or {author (System/getProperty "user.name")
              license "epl"
              target "babel"
              url "https://github.com/"}} opts
        mkdirp (when-not (empty? target) ":mkdirp yes ")
        target (when-not (empty? target) (str target java.io.File/separator))
        group (group-name name)
        opts (merge
              {:name (tpl/project-name name)
               :group group
               :fqname name
               :sanitized (tpl/name-to-path name)
               :author author
               :url url
               :desc "FIXME: write description"
               :license-name (get-in licenses [license :name])
               :license-url (get-in licenses [license :url])
               :ns-root (tpl/sanitize-ns name)
               :ns-root-path (tpl/name-to-path name)
               :tangle-target target
               :tangle-mkdirp mkdirp}
              opts)]
    (main/info (str "Generating fresh literate programming project: " name))
    (opts-info opts
               [:name "generated project dir"
                :group "artefact group ID"
                :url "project url"
                :author "project author"
                :author-url "author url"
                :email "author email"
                :license-name "license"
                :desc "description"
                :tangle-target "path for gen sources"
                :ns-root "project root namespace"])
    (tpl/->files opts
                 ["index.org" (render "index.org" opts)]
                 ["core.org" (render "core.org" opts)]
                 ["test/core.org" (render "test.org" opts)]
                 ["tangle.sh" (render "tangle.sh") :executable true])))
